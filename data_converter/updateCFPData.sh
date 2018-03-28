#!/bin/sh

while [ $# -gt 0 ]; do
    case "$1" in
	"-y" | "-year")
		if [ -z "$2" ]; then
			echo "Error: Year parameter should be specified"
			exit 2
		fi
        yearNode=$2
        shift
        ;;
    "-p" | "--prod")
		echo "Setting flavour to prod"
		databaseUrl="https://android-makers.firebaseio.com/"
        ;;
    "-pp" | "--preprod")
		echo "Setting flavour to preprod"
		databaseUrl="https://androidmakers-preprod.firebaseio.com/"
        ;;
    "-h" | "--help")
		echo "Usage: updateCFPData.sh [-p|-pp] [-y year]"
		exit 0
        ;;
    *)
        echo "Invalid argument: $1"
        exit 1
    esac
    shift
done

if [ -z "$yearNode" ]; then
	echo "No year parameter given, fallback to temporary node"
	yearNode="temp"
fi

if [ -z "$databaseUrl" ]; then
	echo "No flavour selected, fallback to preprod"
	databaseUrl="https://androidmakers-preprod.firebaseio.com/"
fi

finalNodeURL="$databaseUrl$yearNode"

echo "final upload URL = $finalNodeURL"

python -c 'import sys, json; from ruamel.yaml import YAML; yaml=YAML(); json.dump(yaml.load(sys.stdin), sys.stdout, indent=4)' < organizers.yml > organizers.json
python -c 'import sys, json; from ruamel.yaml import YAML; yaml=YAML(); json.dump(yaml.load(sys.stdin), sys.stdout, indent=4)' < partners.yml > partners.json
python -c 'import sys, json; from ruamel.yaml import YAML; yaml=YAML(); json.dump(yaml.load(sys.stdin), sys.stdout, indent=4)' < schedule_timestamp.yml > schedule.json
python -c 'import sys, json; from ruamel.yaml import YAML; yaml=YAML(); json.dump(yaml.load(sys.stdin), sys.stdout, indent=4)' < sessions.yml > sessions.json
python -c 'import sys, json; from ruamel.yaml import YAML; yaml=YAML(); json.dump(yaml.load(sys.stdin), sys.stdout, indent=4)' < speakers.yml > speakers.json
python -c 'import sys, json; from ruamel.yaml import YAML; yaml=YAML(); json.dump(yaml.load(sys.stdin), sys.stdout, indent=4)' < rooms.yml > rooms.json
python -c 'import sys, json; from ruamel.yaml import YAML; yaml=YAML(); json.dump(yaml.load(sys.stdin), sys.stdout, indent=4)' < venues.yml > venues.json
# python -c 'import sys, yaml, json; json.dump(yaml.load(sys.stdin), sys.stdout, indent=4)' < team.yml > team.json

curl -X PUT -s -d @organizers.json "$finalNodeURL/organizers.json" > /dev/null
curl -X PUT -s -d @partners.json "$finalNodeURL/partners.json" > /dev/null
curl -X PUT -s -d @schedule.json "$finalNodeURL/schedule.json" > /dev/null
curl -X PUT -s -d @sessions.json "$finalNodeURL/sessions.json" > /dev/null
curl -X PUT -s -d @speakers.json "$finalNodeURL/speakers.json" > /dev/null
curl -X PUT -s -d @rooms.json "$finalNodeURL/rooms.json" > /dev/null
## curl -X PUT -s -d @team.json "$finalNodeURL/team.json" > /dev/null
curl -X PUT -s -d @venues.json "$finalNodeURL/venues.json" > /dev/null

rm ./*.json
