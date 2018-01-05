# -*- coding: utf-8 -*-
'''
Create on Jan 1st 2018
@Author KEYS
'''

from app.models import User
from app.models import Session
from app.constant import Constant
from app.ulity import Ulity

class UserService(object):

    def __init__(self):
        self.ulity = Ulity()

    def isLogin(self, session_id):
        session_set = Session.objects.filter(SESSION_ID=session_id)
        if len(session_set) == 0:
            return Constant.ERROR_LOGIN_NOLOGIN
        return session_set[0].SESSION_NAME


    def login(self, userid, password):
        user_set_exists = User.objects.filter(USER_ID=userid)
        if len(user_set_exists) == 0:
            return Constant.ERROR_LOGIN_NOEXISTS
        password = self.ulity.encodeMd5(password)
        user_set_correct = User.objects.filter(USER_ID=userid, USER_PASSWORD=password)
        if len(user_set_correct) == 0:
            return Constant.ERROR_LOGIN_INCORRECT
        return Constant.SUCCESS_LOGIN



    def register(self, user):
        user_set = User.objects.filter(USER_ID=user.USER_ID)
        if len(user_set) != 0:
            return Constant.ERROR_REGISTER_EXISTS
        try:
            user.USER_PASSWORD = self.ulity.encodeMd5(user.USER_PASSWORD)
            user.save()
            return Constant.SUCCESS_REGISTER
        except Exception, e:
            return Constant.REGISTER + str(e)


    def logout(self, session_id):
        is_login = self.isLogin(session_id)
        if is_login == Constant.ERROR_LOGIN_NOLOGIN:
            return is_login
        session = Session.objects.get(SESSION_ID=session_id)
        try:
            session.delete()
            return Constant.SUCCESS_LOGOUT
        except:
            return Constant.LOGOUT + Constant.ERROR_DATABASE


    def getUserMessage(self, session_id):
        is_login = self.isLogin(session_id)
        if is_login == Constant.ERROR_LOGIN_NOLOGIN:
            return [is_login,None]
        user_set = User.objects.filter(USER_ID=is_login)
        return [Constant.SUCCESS_GETUSERINFO, user_set[0]]


    def saveSession(self, session):
        is_login = self.isLogin(session.SESSION_ID)
        if is_login == Constant.ERROR_LOGIN_NOLOGIN:
            try:
                session.save()
                return Constant.SUCCESS_LOGIN
            except Exception, e:
                return Constant.SAVESESSION + str(e)
        return Constant.ERROR_LOGIN_HASLOGIN

    def updateUserInfo(self, user):
        user_set = User.objects.filter(USER_ID=user.USER_ID)
        if len(user_set) == 0:
            return Constant.ERROR_LOGIN_NOEXISTS
        try:
            user_set[0].USER_EMAIL = user.USER_EMAIL
            user_set[0].USER_NAME = user.USER_NAME
            user_set[0].USER_PHONE = user.USER_PHONE
            user_set[0].USER_SCHOOL = user.USER_SCHOOL
            user_set[0].save()
            return Constant.SUCCESS_UPDATEUSERINFO
        except Exception, e:
            return Constant.UPDATEUSERINFO + str(e)
