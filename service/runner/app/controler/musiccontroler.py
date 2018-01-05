'''
Create on Jan 1st 2018
@Author KEYS
'''

from app.service.userservice import UserService
from app.service.musicservice import MusicService
from app.ulity import Ulity
from django.http import HttpResponse
from app.models import Music
from app.constant import Constant
from django.views.decorators.csrf import csrf_exempt

import simplejson
import datetime

class MusicControler(object):

    def __init__(self):
        self.uservice = UserService()
        self.ulity = Ulity()
        self.musicservice = MusicService()

    @csrf_exempt
    def createMusicControler(self, request):
        if request.method == 'POST':
            status = 400
            [sessionid, response] = self.ulity.isEmptySession(request.session)
            if sessionid == None:
                response["message"] = Constant.FAIL_CREATEMUSICLIST
                return HttpResponse(content=simplejson.dumps(response), status=status)
            is_login = self.uservice.isLogin(sessionid)
            req = simplejson.loads(request.body)
            if is_login == Constant.ERROR_LOGIN_NOLOGIN:
                response['message'] = Constant.FAIL_CREATEMUSICLIST
                response['data'] = {}
                response['data']['error'] = is_login
            else:
                music = Music(MUSIC_NAME=is_login, MUSIC_LIST=','.join(req["musicname"]))
                create_musiclist_message = self.musicservice.createMusicList(music)
                if create_musiclist_message != Constant.SUCCESS_CREATEMUSICLIST:
                    response["message"] = Constant.FAIL_CREATEMUSICLIST
                    response["data"] = {}
                    response["data"]["error"] = create_musiclist_message
                else:
                    response["message"] = Constant.SUCCESS_CREATEMUSICLIST
                    response["musicid"] = music.MUSIC_ID
                    status = 200
            return HttpResponse(content=simplejson.dumps(response), status=status)


    @csrf_exempt
    def deleteMusicControler(self, request):
        if request.method == 'DELETE':
            status = 400
            [sessionid, response] = self.ulity.isEmptySession(request.session)
            if sessionid == None:
                response["message"] = Constant.FAIL_DELETEMUSICLIST
                return HttpResponse(content=simplejson.dumps(response), status=status)
            is_login = self.uservice.isLogin(sessionid)
            req = simplejson.loads(request.body)
            if is_login == Constant.ERROR_LOGIN_NOLOGIN:
                response['message'] = Constant.FAIL_DELETEMUSICLIST
                response['data'] = {}
                response['data']['error'] = is_login
            else:
                music_id = req["musicid"]
                delete_musiclist_message = self.musicservice.deleteMusicList(music_id, is_login)
                if delete_musiclist_message != Constant.SUCCESS_DELETEMUSICLIST:
                    response["message"] = Constant.FAIL_DELETEMUSICLIST
                    response["data"] = {}
                    response["data"]["error"] = delete_musiclist_message
                else:
                    response["message"] = Constant.SUCCESS_DELETEMUSICLIST
                    status = 200
            return HttpResponse(content=simplejson.dumps(response), status=status)

    @csrf_exempt
    def updateMusicControler(self, request):
        if request.method == 'PUT':
            status = 400
            [sessionid, response] = self.ulity.isEmptySession(request.session)
            if sessionid == None:
                response["message"] = Constant.FAIL_UPDATEMUSICLIST
                return HttpResponse(content=simplejson.dumps(response), status=status)
            is_login = self.uservice.isLogin(sessionid)
            req = simplejson.loads(request.body)
            if is_login == Constant.ERROR_LOGIN_NOLOGIN:
                response['message'] = Constant.FAIL_UPDATEMUSICLIST
                response['data'] = {}
                response['data']['error'] = is_login
            else:
                music = Music(MUSIC_ID=req["musicid"],MUSIC_NAME=is_login, MUSIC_LIST=','.join(req["musicname"]))
                update_musiclist_message = self.musicservice.updateMusicList(music)
                if update_musiclist_message != Constant.SUCCESS_UPDATEMUSICLIST:
                    response["message"] = Constant.FAIL_UPDATEMUSICLIST
                    response["data"] = {}
                    response["data"]["error"] = update_musiclist_message
                else:
                    response["message"] = Constant.SUCCESS_UPDATEMUSICLIST
                    response["musicid"] = music.MUSIC_ID
                    status = 200
            return HttpResponse(content=simplejson.dumps(response), status=status)

    @csrf_exempt
    def getAllMusicControler(self, request):
        if request.method == 'GET':
            status = 400
            [sessionid, response] = self.ulity.isEmptySession(request.session)
            if sessionid == None:
                response["message"] = Constant.FAIL_GETMUSICLIST
                return HttpResponse(content=simplejson.dumps(response), status=status)
            is_login = self.uservice.isLogin(sessionid)
            if is_login == Constant.ERROR_LOGIN_NOLOGIN:
                response['message'] = Constant.FAIL_GETMUSICLIST
                response['data'] = {}
                response['data']['error'] = is_login
            else:
                [get_musiclist_message,music_set] = self.musicservice.getMusciList(is_login)
                if music_set is not None:
                    for music in music_set:
                        music_json = {}
                        music_json["musicid"] = music.MUSIC_ID
                        music_json["musicname"] = music.MUSIC_LIST
                        music_json["musicuser"] = music.MUSIC_NAME
                        response[music.MUSIC_ID] = music_json
                        status = 200
                else:
                    response['message'] = Constant.FAIL_GETMUSICLIST
                    response['data'] = {}
                    response['data']['error'] = get_musiclist_message
            return HttpResponse(content=simplejson.dumps(response), status=status)

    @csrf_exempt
    def difMethodMusicControler(self, request):
        if request.method == 'PUT':
            return self.updateMusicControler(request)
        if request.method == 'DELETE':
            return self.deleteMusicControler(request)
        if request.method == 'POST':
            return self.createMusicControler(request)
        if request.method == 'GET':
            return self.getAllMusicControler(request)