'''
Create on Jan 1st 2018
@Author KEYS
'''
"""runner URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.11/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url
from app.controler.usercontroler import UserControler
from app.controler.plancontroler import PlanControler
from app.controler.commentcontroler import CommentControler
from app.controler.articlecontroler import ArticleControler
from app.controler.musiccontroler import MusicControler

user_controler = UserControler()
plan_controler = PlanControler()
article_controler = ArticleControler()
comment_controler = CommentControler()
music_controler = MusicControler()

urlpatterns = [
    url(r'^api/runner/user/register', user_controler.registerControler),
    url(r'^api/runner/user/login', user_controler.difMethodLogin),
    url(r'^api/runner/user/logout', user_controler.logoutControler),
    url(r'^api/runner/user', user_controler.difMethodInfo),
    url(r'^api/runner/plan', plan_controler.difMethodPlanControler),
    url(r'^api/runner/article', article_controler.difMethodArticleControler),
    url(r'^api/runner/comment', comment_controler.difMethodCommentControler),
    url(r'^api/runner/music', music_controler.difMethodMusicControler),
]
