'''
Create on Jan 1st 2018
@Author KEYS
'''

from app.service.userservice import UserService
from app.service.articleservice import ArticleService
from app.ulity import Ulity
from django.http import HttpResponse
from app.models import Article
from app.constant import Constant
from django.views.decorators.csrf import csrf_exempt

import simplejson

class ArticleControler:

    def __init__(self):
        self.uservice = UserService()
        self.ulity = Ulity()
        self.articleservice = ArticleService()


    @csrf_exempt
    def createArticleControler(self, request):
        if request.method == 'POST':
            status = 200
            [sessionid, response] = self.ulity.isEmptySession(request.session)
            if sessionid == None:
                response["message"] = Constant.FAIL_CREATEARTICLE
                return HttpResponse(content=simplejson.dumps(response), status=status)
            is_login = self.uservice.isLogin(sessionid)
            req = simplejson.loads(request.body)
            if is_login == Constant.ERROR_LOGIN_NOLOGIN:
                response['message'] = Constant.FAIL_CREATEARTICLE
                response['data'] = {}
                response['data']['error'] = is_login
            else:
                article = Article(ARTICLE_TITLE=req["title"], ARTICLE_AUTHOR=is_login, ARTICLE_CONTENT=req["content"])
                create_article_message = self.articleservice.createArticle(article)
                if create_article_message != Constant.SUCCESS_CREATEARTICLE:
                    response["message"] = Constant.FAIL_CREATEARTICLE
                    response["data"] = {}
                    response["data"]["error"] = create_article_message
                else:
                    response["message"] = Constant.SUCCESS_CREATEARTICLE
                    response["articleid"] = article.ARTICLE_ID
                    status = 200
            return HttpResponse(content=simplejson.dumps(response), status=status)


    @csrf_exempt
    def deleteArticleControler(self, request):
        if request.method == 'DELETE':
            status = 200
            [sessionid, response] = self.ulity.isEmptySession(request.session)
            if sessionid == None:
                response["message"] = Constant.FAIL_DELETEARTICLE
                return HttpResponse(content=simplejson.dumps(response), status=status)
            is_login = self.uservice.isLogin(sessionid)
            req = simplejson.loads(request.body)
            if is_login == Constant.ERROR_LOGIN_NOLOGIN:
                response['message'] = Constant.FAIL_DELETEARTICLE
                response['data'] = {}
                response['data']['error'] = is_login
            else:
                article_id = req["articleid"]
                delete_article_message = self.articleservice.deleteArticle(article_id, is_login)
                if delete_article_message != Constant.SUCCESS_DELETEARTICLE:
                    response["message"] = Constant.FAIL_DELETEARTICLE
                    response["data"] = {}
                    response["data"]["error"] = delete_article_message
                else:
                    response["message"] = Constant.SUCCESS_DELETEARTICLE
                    status = 200
            return HttpResponse(content=simplejson.dumps(response), status=status)


    @csrf_exempt
    # def updateArticleControler(self, request):
    #     if request.method == 'PUT':
    #         [sessionid, response] = self.ulity.isEmptySession(request.session)
    #         if sessionid == None:
    #             response["message"] = Constant.FAIL_UPDATEARTICLE
    #             return HttpResponse(simplejson.dumps(response))
    #         is_login = self.uservice.isLogin(sessionid)
    #         req = simplejson.loads(request.body)
    #         if is_login == Constant.ERROR_LOGIN_NOLOGIN:
    #             response['message'] = Constant.FAIL_UPDATEARTICLE
    #             response['data'] = {}
    #             response['data']['error'] = is_login
    #         else:
    #             article = Article(ARTICLE_TITLE=req["title"], ARTICLE_AUTHOR=is_login, ARTICLE_CONTENT=req["content"])
    #             update_article_message = self.articleservice.updateArticle(article)
    #             if update_article_message != Constant.SUCCESS_UPDATEPLAN:
    #                 response["message"] = Constant.FAIL_UPDATEARTICLE
    #                 response["data"] = {}
    #                 response["data"]["error"] = update_article_message
    #             else:
    #                 response["message"] = Constant.SUCCESS_UPDATEARTICLE
    #                 response["planid"] = article.ARTICLE_ID
    #         return HttpResponse(simplejson.dumps(response))


    @csrf_exempt
    def getAllArticleControler(self, request):
        if request.method == 'GET':
            status = 200
            [sessionid, response] = self.ulity.isEmptySession(request.session)
            if sessionid == None:
                response["message"] = Constant.FAIL_GETALLARTICLE
                return HttpResponse(content=simplejson.dumps(response), status=status)
            is_login = self.uservice.isLogin(sessionid)
            if is_login == Constant.ERROR_LOGIN_NOLOGIN:
                response['message'] = Constant.FAIL_GETALLARTICLE
                response['data'] = {}
                response['data']['error'] = is_login
            else:
                [get_allarticle_message, article_set] = self.articleservice.getAllArticle(is_login)
                if article_set is not None:
                    for article in article_set:
                        article_json = {}
                        article_json["articleid"] = article.ARTICLE_ID
                        article_json["title"] = article.ARTICLE_TITLE
                        article_json["author"] = article.ARTICLE_AUTHOR
                        article_json["addtime"] = article.ADD_DATETIME.strftime('%Y-%m-%d %H:%M')
                        response[article.ARTICLE_ID] = article_json
                        status = 200
                else:
                    response['message'] = Constant.FAIL_GETALLARTICLE
                    response['data'] = {}
                    response['data']['error'] = get_allarticle_message
            return HttpResponse(content=simplejson.dumps(response), status=status)


    @csrf_exempt
    def difMethodArticleControler(self, request):
        # if request.method == 'PUT':
        #     return self.updateArticleControler(request)
        if request.method == 'DELETE':
            return self.deleteArticleControler(request)
        if request.method == 'POST':
            return self.createArticleControler(request)
        if request.method == 'GET':
            return self.getAllArticleControler(request)
