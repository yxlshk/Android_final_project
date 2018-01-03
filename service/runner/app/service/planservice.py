# -*- coding: utf-8 -*-
'''
Create on Jan 1st 2018
@Author KEYS
'''

from app.models import Plan
from app.constant import Constant
from app.ulity import Ulity

class PlanService(object):

    def __init__(self):
        self.ulity = Ulity()

    def createPlan(self, plan):
        plan_set = Plan.objects.filter(PLAN_USER=plan.PLAN_USER,RUN_DATETIME=plan.RUN_DATETIME)
        if len(plan_set) != 0:
            return Constant.ERROR_PLAN_CONFLICT
        try:
            plan.save()
            return Constant.SUCCESS_CREATEPLAN
        except Exception, e:
            return Constant.CREATEPLAN + str(e)

    def updatePlan(self, plan):
        plan_set = Plan.objects.filter(PLAN_ID=plan.PLAN_ID)
        if len(plan_set) == 0:
            return Constant.ERROR_PLAN_NOEXISTS
        plan_set[0].PLAN_NAME = plan.PLAN_NAME
        plan_set[0].PLAN_USER = plan.PLAN_USER
        plan_set[0].RUN_DATETIME = plan.RUN_DATETIME
        plan_set[0].PLACE = plan.PLACE
        plan_set[0].PARTNER = plan.PARTNER
        try:
            plan_set[0].save()
            return Constant.SUCCESS_UPDATEPLAN
        except Exception, e:
            return Constant.UPDATEPLAN + str(e)

    def deletePlan(self, plan_id, plan_user):
        plan_set = Plan.objects.filter(PLAN_ID=plan_id, PLAN_USER=plan_user)
        if len(plan_set) == 0:
            return Constant.ERROR_PLAN_NOEXISTS
        try:
            Plan.objects.get(PLAN_ID=plan_id).delete()
            return Constant.SUCCESS_DELETEPLAN
        except Exception, e:
            return Constant.DELETEPLAN + str(e)

    def getAllPlans(self, plan_author):
        plan_set = Plan.objects.filter(PLAN_USER=plan_author)
        if len(plan_set) == 0:
            return [Constant.ERROR_PLAN_NOEXISTS, None]
        return [Constant.SUCCESS_GETALLPLANS,plan_set]