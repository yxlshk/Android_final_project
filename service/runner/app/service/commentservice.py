# -*- coding: utf-8 -*-
'''
Create on Jan 1st 2018
@Author KEYS
'''

from app.models import Comment
from app.constant import Constant
from app.ulity import Ulity

class CommentsService(object):

    def __init__(self):
        self.ulity = Ulity()

    def createComment(self, comment):
        try:
            comment.save()
            return Constant.SUCCESS_CREATECOMMENT
        except Exception, e:
            return Constant.CREATECOMMENT + str(e)

    def deleteComment(self, comment_id, article_id, comment_author):
        comment_set = Comment.objects.filter(COMMENT_AUTHOR=comment_author, COMMENT_ID=comment_id, ARTICLE_ID=article_id)
        if len(comment_set) == 0:
            return Constant.ERROR_COMMENT_NOEXISTS
        try:
            comment_set[0].delete()
            return Constant.SUCCESS_DELETECOMMENT
        except Exception, e:
            return Constant.DELETECOMMENT + str(e)

    def getAllComment(self, article_id):
        comment_set = Comment.objects.filter(ARTICLE_ID=article_id)
        if len(comment_set) == 0:
            return [Constant.ERROR_COMMENT_NOEXISTS, None]
        return [Constant.SUCCESS_GETALLCOMMENTS, comment_set]