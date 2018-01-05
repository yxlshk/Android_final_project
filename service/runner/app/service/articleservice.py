# -*- coding: utf-8 -*-
'''
Create on Jan 1st 2018
@Author KEYS
'''

from app.models import Article
from app.constant import Constant
from app.ulity import Ulity

class ArticleService(object):

    def __init__(self):
        self.ulity = Ulity()

    def createArticle(self, article):
        article_set = Article.objects.filter(ARTICLE_AUTHOR=article.ARTICLE_AUTHOR, ARTICLE_TITLE=article.ARTICLE_TITLE)
        if len(article_set) != 0:
            return Constant.ERROR_ARTICLE_CONFLICT
        try:
            article.save()
            return Constant.SUCCESS_CREATEARTICLE
        except Exception, e:
            return Constant.CREATEARTICLE + str(e)

    def updateArticle(self, article):
        article_set = Article.objects.filter(ARTICLE_ID=article.ARTICLE_ID)
        if len(article_set) == 0:
            return Constant.ERROR_ARTICLE_NOEXISTS
        article_set[0].ARTICLE_TITLE = article.ARTICLE_TITLE
        article_set[0].ARTICLE_AUTHOR = article.ARTICLE_AUTHOR
        article_set[0].ARTICLE_CONTENT = article.ARTICLE_CONTENT
        try:
            article_set[0].save()
            return Constant.SUCCESS_UPDATEARTICLE
        except Exception, e:
            return Constant.UPDATEARTICLE + str(e)

    def deleteArticle(self, article_id, author):
        article_set = Article.objects.filter(ARTICLE_ID=article_id, ARTICLE_AUTHOR=author)
        if len(article_set) == 0:
            return Constant.ERROR_ARTICLE_NOEXISTS
        try:
            article_set[0].delete()
            return Constant.SUCCESS_DELETEARTICLE
        except Exception, e:
            return Constant.DELETEARTICLE + str(e)

    def getAllArticle(self, author):
        article_set = Article.objects.filter(ARTICLE_AUTHOR=author)
        if len(article_set) == 0:
            return [Constant.ERROR_ARTICLE_NOEXISTS, None]
        return [Constant.SUCCESS_GETALLARTICLE, article_set]
