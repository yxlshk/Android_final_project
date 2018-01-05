# -*- coding: utf-8 -*-

from app.service.planservice import PlanService
from app.models import Plan
import datetime

if __name__ == "__main__":
    planservice = PlanService()
    str = '2018-01-01 10:10'
    date_time = datetime.datetime.strptime(str, '%Y-%m-%d %H:%M')
    plan = Plan(PLAN_USER='15331411', PLAN_NAME='跑步', RUN_DATETIME=date_time, PLACE='假草', PARTNER='李四')
    print plan.PLAN_ID