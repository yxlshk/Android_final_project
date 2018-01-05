# -*- coding: utf-8 -*-
'''
Create on Jan 1st 2018
@Author KEYS
'''

from app.models import Music
from app.constant import Constant
from app.ulity import Ulity

class MusicService(object):

    def __init__(self):
        self.ulity = Ulity()

    def createMusicList(self, music):
        music_set = Music.objects.filter(MUSIC_NAME=music.MUSIC_NAME)
        if len(music_set) != 0:
            return Constant.ERROR_MUSICLIST_CONFLICT
        try:
            music.save()
            return Constant.SUCCESS_CREATEMUSICLIST
        except Exception, e:
            return Constant.CREATEMUSICLIST + str(e)

    def updateMusicList(self, music):
        music_set = Music.objects.filter(MUSIC_ID=music.MUSIC_ID)
        if len(music_set) == 0:
            return Constant.ERROR_MUSICLIST_NOEXISTS
        music_set[0].MUSIC_LIST = music.MUSIC_LIST
        music_set[0].MUSIC_NAME = music.MUSIC_NAME
        try:
            music_set[0].save()
            return Constant.SUCCESS_UPDATEMUSICLIST
        except Exception, e:
            return Constant.UPDATEMUSICLIST + str(e)

    def deleteMusicList(self, music_id, music_name):
        music_set = Music.objects.filter(MUSIC_ID=music_id, MUSIC_NAME=music_name)
        if len(music_set) == 0:
            return Constant.ERROR_MUSICLIST_NOEXISTS
        try:
            music_set[0].delete()
            return Constant.SUCCESS_DELETEMUSICLIST
        except Exception, e:
            return Constant.DELETEMUSICLIST + str(e)

    def getMusciList(self, music_name):
        music_set = Music.objects.filter(MUSIC_NAME=music_name)
        if len(music_set) == 0:
            return [Constant.ERROR_MUSICLIST_NOEXISTS, None]
        return [Constant.SUCCESS_GETMUSICLIST, music_set]