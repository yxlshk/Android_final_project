'''
Create on Jan 1st 2018
@Author KEYS
'''

from app.service.userservice import UserService
from app.service.commentservice import CommentsService
from app.ulity import Ulity
from django.http import HttpResponse
from app.models import Comment
from app.constant import Constant
from django.views.decorators.csrf import csrf_exempt

import simplejson

class CommentControler(object):

    def __init__(self):
        self.uservice = UserService()
        self.ulity = Ulity()
        self.commentservice = CommentsService()

    @csrf_exempt
    def createCommentControler(self, request):
        if request.method == 'POST':
            status = 400
            [sessionid, response] = self.ulity.isEmptySession(request.session)
            if sessionid == None:
                response["message"] = Constant.FAIL_CREATECOMMENT
                return HttpResponse(content=simplejson.dumps(response), status=status)
            is_login = self.uservice.isLogin(sessionid)
            req = simplejson.loads(request.body)
            if is_login == Constant.ERROR_LOGIN_NOLOGIN:
                response['message'] = Constant.FAIL_CREATECOMMENT
                response['data'] = {}
                response['data']['error'] = is_login
            else:
                comment = Comment(ARTICLE_ID=req["articleid"], COMMENT_AUTHOR=is_login, COMMENT_CONTENT=req["content"])
                create_comment_message = self.commentservice.createComment(comment)
                if create_comment_message != Constant.SUCCESS_CREATECOMMENT:
                    response["message"] = Constant.FAIL_CREATECOMMENT
                    response["data"] = {}
                    response["data"]["error"] = create_comment_message
                else:
                    response["message"] = Constant.SUCCESS_CREATECOMMENT
                    response["commentid"] = comment.COMMENT_ID
                    status = 200
            return HttpResponse(content=simplejson.dumps(response), status=status)


    @csrf_exempt
    def deleteCommentControler(self, request):
        if request.method == 'DELETE':
            status = 400
            [sessionid, response] = self.ulity.isEmptySession(request.session)
            if sessionid == None:
                response["message"] = Constant.FAIL_DELETECOMMENT
                return HttpResponse(content=simplejson.dumps(response), status=status)
            is_login = self.uservice.isLogin(sessionid)
            req = simplejson.loads(request.body)
            if is_login == Constant.ERROR_LOGIN_NOLOGIN:
                response['message'] = Constant.FAIL_DELETECOMMENT
                response['data'] = {}
                response['data']['error'] = is_login
            else:
                delete_comment_message = self.commentservice.deleteComment(req["commentid"], req["articleid"],is_login)
                if delete_comment_message != Constant.SUCCESS_DELETECOMMENT:
                    response["message"] = Constant.FAIL_DELETECOMMENT
                    response["data"] = {}
                    response["data"]["error"] = delete_comment_message
                else:
                    response["message"] = Constant.SUCCESS_DELETECOMMENT
                    status = 200
            return HttpResponse(content=simplejson.dumps(response), status=status)

    @csrf_exempt
    def getAllCommentControler(self, request):
        if request.method == 'GET':
            status = 400
            [sessionid, response] = self.ulity.isEmptySession(request.session)
            if sessionid == None:
                response["message"] = Constant.FAIL_GETALLCOMMENTS
                return HttpResponse(content=simplejson.dumps(response), status=status)
            is_login = self.uservice.isLogin(sessionid)
            if is_login == Constant.ERROR_LOGIN_NOLOGIN:
                response['message'] = Constant.FAIL_GETALLCOMMENTS
                response['data'] = {}
                response['data']['error'] = is_login
            else:
                [get_allcomment_message,comment_set] = self.commentservice.getAllComment(request.GET.get("articleid"))
                if comment_set is not None:
                    for comment in comment_set:
                        comment_json = {}
                        comment_json["commentid"] = comment.COMMENT_ID
                        comment_json["articleid"] = comment.ARTICLE_ID
                        comment_json["author"] = comment.COMMENT_AUTHOR
                        comment_json["content"] = comment.COMMENT_CONTENT
                        comment_json["addtime"] = comment.ADD_DATETIME.strftime('%Y-%m-%d %H:%M')
                        response[comment.COMMENT_ID] = comment_json
                        status = 200
                else:
                    response['message'] = Constant.FAIL_GETALLCOMMENTS
                    response['data'] = {}
                    response['data']['error'] = get_allcomment_message
            return HttpResponse(content=simplejson.dumps(response), status=status)

    @csrf_exempt
    def difMethodCommentControler(self, request):
        if request.method == 'DELETE':
            return self.deleteCommentControler(request)
        if request.method == 'POST':
            return self.createCommentControler(request)
        if request.method == 'GET':
            return self.getAllCommentControler(request)