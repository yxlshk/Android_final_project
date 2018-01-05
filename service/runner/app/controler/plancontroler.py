'''
Create on Jan 1st 2018
@Author KEYS
'''

from app.service.userservice import UserService
from app.service.planservice import PlanService
from app.ulity import Ulity
from django.http import HttpResponse
from app.models import Plan, Session
from app.constant import Constant
from django.views.decorators.csrf import csrf_exempt

import simplejson
import datetime

class PlanControler(object):

    def __init__(self):
        self.uservice = UserService()
        self.ulity = Ulity()
        self.planservice = PlanService()

    @csrf_exempt
    def createPlanControler(self, request):
        if request.method == 'POST':
            [sessionid, response] = self.ulity.isEmptySession(request.session)
            if sessionid == None:
                response["message"] = Constant.FAIL_CREATEPLAN
                return HttpResponse(simplejson.dumps(response))
            is_login = self.uservice.isLogin(sessionid)
            req = simplejson.loads(request.body)
            if is_login == Constant.ERROR_LOGIN_NOLOGIN:
                response['message'] = Constant.FAIL_CREATEPLAN
                response['data'] = {}
                response['data']['error'] = is_login
            else:
                partner = req["partner"]
                partner_str = ','.join(partner)
                plan = Plan(PLAN_USER=is_login, PLAN_NAME=req["planname"],
                            RUN_DATETIME=datetime.datetime.strptime(req["runtime"],'%Y-%m-%d %H:%M'),
                            PARTNER=partner_str, PLACE=req["place"])
                create_plan_message = self.planservice.createPlan(plan)
                if create_plan_message != Constant.SUCCESS_CREATEPLAN:
                    response["message"] = Constant.FAIL_CREATEPLAN
                    response["data"] = {}
                    response["data"]["error"] = create_plan_message
                else:
                    response["message"] = Constant.SUCCESS_CREATEPLAN
                    response["planid"] = plan.PLAN_ID
            return HttpResponse(simplejson.dumps(response))


    @csrf_exempt
    def deletePlanControler(self, request):
        if request.method == 'DELETE':
            [sessionid, response] = self.ulity.isEmptySession(request.session)
            if sessionid == None:
                response["message"] = Constant.FAIL_DELETEPLAN
                return HttpResponse(simplejson.dumps(response))
            is_login = self.uservice.isLogin(sessionid)
            req = simplejson.loads(request.body)
            if is_login == Constant.ERROR_LOGIN_NOLOGIN:
                response['message'] = Constant.FAIL_DELETEPLAN
                response['data'] = {}
                response['data']['error'] = is_login
            else:
                plan_id = req["planid"]
                delete_plan_message = self.planservice.deletePlan(plan_id, is_login)
                if delete_plan_message != Constant.SUCCESS_DELETEPLAN:
                    response["message"] = Constant.FAIL_DELETEPLAN
                    response["data"] = {}
                    response["data"]["error"] = delete_plan_message
                else:
                    response["message"] = Constant.SUCCESS_DELETEPLAN
            return HttpResponse(simplejson.dumps(response))

    @csrf_exempt
    def updatePlanControler(self, request):
        if request.method == 'PUT':
            [sessionid, response] = self.ulity.isEmptySession(request.session)
            if sessionid == None:
                response["message"] = Constant.FAIL_UPDATEPLAN
                return HttpResponse(simplejson.dumps(response))
            is_login = self.uservice.isLogin(sessionid)
            req = simplejson.loads(request.body)
            if is_login == Constant.ERROR_LOGIN_NOLOGIN:
                response['message'] = Constant.FAIL_UPDATEPLAN
                response['data'] = {}
                response['data']['error'] = is_login
            else:
                partner = req["partner"]
                partner_str = ','.join(partner)
                plan = Plan(PLAN_ID=req["planid"], PLAN_USER=is_login, PLAN_NAME=req["planname"],
                            RUN_DATETIME=datetime.datetime.strptime(req["runtime"],'%Y-%m-%d %H:%M'),
                            PARTNER=partner_str, PLACE=req["place"])
                update_plan_message = self.planservice.updatePlan(plan)
                if update_plan_message != Constant.SUCCESS_UPDATEPLAN:
                    response["message"] = Constant.FAIL_UPDATEPLAN
                    response["data"] = {}
                    response["data"]["error"] = update_plan_message
                else:
                    response["message"] = Constant.SUCCESS_UPDATEPLAN
                    response["planid"] = plan.PLAN_ID
            return HttpResponse(simplejson.dumps(response))

    @csrf_exempt
    def getAllPlanControler(self, request):
        if request.method == 'GET':
            [sessionid, response] = self.ulity.isEmptySession(request.session)
            if sessionid == None:
                response["message"] = Constant.FAIL_GETALLPLANS
                return HttpResponse(simplejson.dumps(response))
            is_login = self.uservice.isLogin(sessionid)
            if is_login == Constant.ERROR_LOGIN_NOLOGIN:
                response['message'] = Constant.FAIL_GETALLPLANS
                response['data'] = {}
                response['data']['error'] = is_login
            else:
                [get_allplan_message,plan_set] = self.planservice.getAllPlans(is_login)
                if plan_set is not None:
                    for plan in plan_set:
                        plan_json = {}
                        plan_json["planid"] = plan.PLAN_ID
                        plan_json["planname"] = plan.PLAN_NAME
                        plan_json["author"] = plan.PLAN_NAME
                        plan_json["runtime"] = plan.RUN_DATETIME.strftime('%Y-%m-%d %H:%M')
                        plan_json["partner"] = plan.PARTNER.split(',')
                        plan_json["place"] = plan.PLACE
                        plan_json["addtime"] = plan.ADD_DATETIME.strftime('%Y-%m-%d %H:%M')
                        response[plan.PLAN_ID] = plan_json
                else:
                    response['message'] = Constant.FAIL_GETALLPLANS
                    response['data'] = {}
                    response['data']['error'] = get_allplan_message
            return HttpResponse(simplejson.dumps(response))

    @csrf_exempt
    def difMethodPlanControler(self, request):
        if request.method == 'PUT':
            return self.updatePlanControler(request)
        if request.method == 'DELETE':
            return self.deletePlanControler(request)
        if request.method == 'POST':
            return self.createPlanControler(request)
        if request.method == 'GET':
            return self.getAllPlanControler(request)