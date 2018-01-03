'''
Create on Jan 1st 2018
@Author KEYS
'''

from app.service.userservice import UserService
from app.ulity import Ulity
from django.http import HttpResponse
from app.models import User, Session
from app.constant import Constant
from django.views.decorators.csrf import csrf_exempt

import simplejson

class UserControler(object):

    def __init__(self):
        self.uservice = UserService()
        self.ulity = Ulity()

    @csrf_exempt
    def registerControler(self, request):
        if request.method == 'POST':
            req = simplejson.loads(request.body)
            user = User(USER_ID=req["userid"], USER_NAME=req["username"],
                        USER_PASSWORD=req["password"], USER_SCHOOL=req["school"],
                        USER_EMAIL=req["email"], USER_PHONE=req["phone"]
                        )
            response = {}
            message = self.uservice.register(user)
            if message == Constant.SUCCESS_REGISTER:
                response["message"] = message
            else:
                response["message"] = Constant.FAIL_REGISTER;
                response["data"] = {}
                response["data"]["error"] = message
            return HttpResponse(simplejson.dumps(response))

    @csrf_exempt
    def loginControler(self, request):
        if request.method == 'POST':
            req = simplejson.loads(request.body)
            username = req["userid"]
            password = req["password"]
            response = {}
            try:
                session_id = request.session["sessionid"]
                is_login = self.uservice.isLogin(session_id)
                if is_login == username:
                    response["message"] = Constant.FAIL_LOGIN
                    response["data"] = {}
                    response["data"]["error"] = Constant.ERROR_LOGIN_HASLOGIN
                else:
                    response["message"] = Constant.FAIL_LOGIN
                    response["data"] = {}
                    response["data"]["error"] = Constant.ERROR_LOGIN_HASOTHERLOGIN
                return HttpResponse(simplejson.dumps(response))
            except KeyError, e:
                pass
            login = self.uservice.login(username, password)

            if login == Constant.SUCCESS_LOGIN:
                session_id = self.ulity.createSessionId()
                session = Session(SESSION_ID=session_id, SESSION_NAME=username)
                message = self.uservice.saveSession(session)
                if message == Constant.SUCCESS_LOGIN:
                    response["message"] = message
                    request.session["sessionid"] = str(session_id)
                else:
                    response["message"] = Constant.FAIL_LOGIN
                    response["data"] = {}
                    response["data"]["error"] = message
            else:
                response["message"] = Constant.FAIL_LOGIN
                response["data"] = {}
                response["data"]["error"] = login
            return HttpResponse(simplejson.dumps(response))

    @csrf_exempt
    def logoutControler(self, request):
        if request.method == 'POST':
            response = {}
            try:
                session_id = request.session["sessionid"]
            except KeyError, e:
                response["message"] = Constant.FAIL_LOGOUT
                response["data"] = {}
                response["data"]["error"] = Constant.ERROR_LOGIN_NOLOGIN
                return HttpResponse(simplejson.dumps(response))

            is_login = self.uservice.isLogin(session_id)
            if is_login == Constant.ERROR_LOGIN_NOLOGIN:
                response["message"] = Constant.FAIL_LOGOUT
                response["data"] = {}
                response["data"]["error"] = is_login
            else:
                response["message"] = Constant.SUCCESS_LOGOUT
                self.uservice.logout(session_id)
                del request.session['sessionid']
            return HttpResponse(simplejson.dumps(response))

    @csrf_exempt
    def getUserMessageControler(self, request):
        if request.method == 'GET':
            response = {}
            try:
                session_id = request.session["sessionid"]
            except KeyError, e:
                response["message"] = Constant.FAIL_GETUSERINFO
                response["data"] = {}
                response["data"]["error"] = Constant.ERROR_LOGIN_NOLOGIN
                return HttpResponse(simplejson.dumps(response))
            user_id = request.GET.get("userid")

            [message, user_set] = self.uservice.getUserMessage(session_id)
            if user_set is not None:
                if user_id == user_set.USER_ID:
                    response["userid"] = user_set.USER_ID
                    response["username"] = user_set.USER_NAME
                    response["school"] = user_set.USER_SCHOOL
                    response["email"] = user_set.USER_EMAIL
                    response["phone"] = user_set.USER_PHONE
                else:
                    response["message"] = Constant.FAIL_GETUSERINFO
                    response["data"] = {}
                    response["data"]["error"] = Constant.ERROR_LOGIN_NOLOGIN
            else:
                response["message"] = Constant.FAIL_GETUSERINFO
                response["data"] = {}
                response["data"]["error"] = message
            return HttpResponse(simplejson.dumps(response))
