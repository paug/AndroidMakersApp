"""
Convert the schedule JSON used on website to adapted format for app.
source: https://github.com/paug/android-makers-2019/blob/master/data/database/schedule.json
"""

#!/usr/bin/env python

import json

from datetime import timedelta, datetime, tzinfo
import pytz

input_schedule_raw = "schedule.json"
output_schedule_app = "schedule-app.json"

rooms = ["moebius", "blin", "202", "204"]

talks = []

def convertDate(datetime_str):
	# https://stackabuse.com/converting-strings-to-datetime-in-python/
	date_time_obj = datetime.strptime(datetime_str, '%Y-%m-%d %H:%M')
	timezone = pytz.timezone('Europe/Paris')  
	timezone_date_time_obj = timezone.localize(date_time_obj)
	return timezone_date_time_obj.isoformat()

def extractTalks(day_str, timeslots):
	for slot in timeslots:
		start_date_string = "{} {}".format(day_str, slot["startTime"])
		end_date_string = "{} {}".format(day_str, slot["endTime"])

		sessions = slot["sessions"]
		if (len(sessions) == 1):
			talk = {}

			items = sessions[0]["items"]
			talk["startDate"] = convertDate(start_date_string)
			talk["endDate"] = convertDate(end_date_string)
			talk["roomId"] = "all"
			talk["sessionId"] = items[0]
			talks.append(talk)
		if (len(sessions) == 4):
			for session in sessions:
				items = session["items"]
				session_index = sessions.index(session)

				if (len(items) > 0):
					talk = {}
					talk["startDate"] = convertDate(start_date_string)
					talk["roomId"] = rooms[session_index]
					talk["sessionId"] = items[0]
					if (session.get("extend") != None):
						slot_index = timeslots.index(slot)
						extend_slot = timeslots[slot_index + session["extend"] - 1]
						extend_end_date_string = "{} {}".format(day_str, extend_slot["endTime"])
						talk["endDate"] = convertDate(extend_end_date_string)
					else:
						talk["endDate"] = convertDate(end_date_string)

					talks.append(talk)

with open(input_schedule_raw) as json_data:
	schedule = json.load(json_data)
	day_1 = schedule["2019-04-23"]
	day_2 = schedule["2019-04-24"]

	timeslots_day_1 = day_1["timeslots"]
	timeslots_day_2 = day_2["timeslots"]

	extractTalks("2019-04-23", timeslots_day_1)
	extractTalks("2019-04-24", timeslots_day_2)
	
with open(output_schedule_app, 'wb') as outfile:
	json.dump(talks, outfile, sort_keys=True, indent=4)
