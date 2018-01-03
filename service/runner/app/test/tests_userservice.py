# -*- coding: utf-8 -*-
'''
Create on Jan 1st 2018
@Author KEYS
'''

from django.test import TestCase, Client
from app.service.userservice import UserService

class UserServiceTestCase(TestCase):
    #测试函数执行前执行
    def setUp(self):
        print ("======in setUp")

    #需要测试的内容
    def test_add(self):
        c = Client()
        rep = c.post("/api/runner/user/register", {'userid':'15331411', 'username':'张泽棉', 'password':'zzm15331411', 'school':'中山大学', 'email':'1106066690@qq.com', 'phone':'15018377821'})
        self.assertEqual(rep.status_code, 200)
        self.assertContains(rep, "Register")
        print rep