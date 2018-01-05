# -*- coding: utf-8 -*-
'''
Create on Jan 1st 2018
@Author KEYS
'''
from __future__ import unicode_literals

from django.db import models

# Create your models here.

class User(models.Model):
    USER_ID = models.CharField(max_length=20, primary_key=True)
    USER_NAME = models.TextField()
    USER_PASSWORD = models.CharField(max_length=200)
    USER_SCHOOL = models.TextField()
    USER_PHONE = models.CharField(max_length=11)
    USER_EMAIL = models.EmailField()

    def __unicode__(self):
        return self.USER_ID

class Session(models.Model):
    SESSION_ID = models.UUIDField(max_length=100, primary_key=True)
    SESSION_NAME = models.CharField(max_length=20)

    def __unicode__(self):
        return self.SESSION_NAME

class Plan(models.Model):
    PLAN_ID = models.AutoField(primary_key=True)
    PLAN_USER = models.CharField(max_length=20)
    PLAN_NAME = models.TextField()
    ADD_DATETIME = models.DateTimeField(auto_now=True)
    RUN_DATETIME = models.DateTimeField()
    PLACE = models.TextField()
    PARTNER = models.TextField()

    def __unicode__(self):
        return self.PLAN_NAME

class Music(models.Model):
    MUSIC_ID = models.AutoField(primary_key=True)
    MUSIC_NAME = models.CharField(max_length=20)
    MUSIC_LIST = models.TextField()

    def __unicode__(self):
        return self.MUSIC_LIST

class Article(models.Model):
    ARTICLE_ID = models.AutoField(primary_key=True)
    ARTICLE_TITLE = models.TextField()
    ARTICLE_AUTHOR = models.CharField(max_length=20)
    ARTICLE_CONTENT = models.TextField()
    ADD_DATETIME = models.DateTimeField(auto_now=True)

    def __unicode__(self):
        return self.ARTICLE_TITLE

class Comment(models.Model):
    COMMENT_ID = models.AutoField(primary_key=True)
    ARTICLE_ID = models.IntegerField()
    COMMENT_AUTHOR = models.CharField(max_length=20)
    COMMENT_CONTENT = models.TextField()
    ADD_DATETIME = models.DateTimeField(auto_now=True)

    def __unicode__(self):
        return self.COMMENT_ID