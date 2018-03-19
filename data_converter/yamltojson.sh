python -c 'import sys, yaml, json; json.dump(yaml.load(sys.stdin), sys.stdout, indent=4)' < organizers.yml > organizers.json
python -c 'import sys, yaml, json; json.dump(yaml.load(sys.stdin), sys.stdout, indent=4)' < partners.yml > partners.json
python -c 'import sys, yaml, json; json.dump(yaml.load(sys.stdin), sys.stdout, indent=4)' < schedule.yml > schedule.json
python -c 'import sys, yaml, json; json.dump(yaml.load(sys.stdin), sys.stdout, indent=4)' < sessions.yml > sessions.json
python -c 'import sys, yaml, json; json.dump(yaml.load(sys.stdin), sys.stdout, indent=4)' < speakers.yml > speakers.json
python -c 'import sys, yaml, json; json.dump(yaml.load(sys.stdin), sys.stdout, indent=4)' < team.yml > team.json

curl -X PUT -s -d @organizers.json 'https://android-makers.firebaseio.com/organizers.json' > /dev/null
curl -X PUT -s -d @partners.json 'https://android-makers.firebaseio.com/partners.json' > /dev/null
curl -X PUT -s -d @schedule.json 'https://android-makers.firebaseio.com/schedule.json' > /dev/null
curl -X PUT -s -d @sessions.json 'https://android-makers.firebaseio.com/sessions.json' > /dev/null
curl -X PUT -s -d @speakers.json 'https://android-makers.firebaseio.com/speakers.json' > /dev/null
curl -X PUT -s -d @team.json 'https://android-makers.firebaseio.com/team.json' > /dev/null

rm *.json
