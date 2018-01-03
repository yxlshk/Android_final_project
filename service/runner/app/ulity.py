'''
Create on Jan 1st 2018
@Author KEYS
'''

import hashlib
import uuid
from app.models import Session
from app.constant import Constant

class Ulity(object):

    def __init__(self):
        pass

    def encodeMd5(self, password):
        password_md5 = hashlib.md5(password).hexdigest()
        return password_md5

    def createSessionId(self):
        while True:
            session_id = uuid.uuid4()
            session_set = Session.objects.filter(SESSION_ID=session_id)
            if len(session_set) == 0:
                return session_id

    def isEmptySession(self, session):
        try:
            session_id = session["sessionid"]
            return [session_id, {}]
        except KeyError, e:
            response = {}
            response["data"] = {}
            response["data"]["error"] = Constant.ERROR_LOGIN_NOLOGIN
            return [None, response]