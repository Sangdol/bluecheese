.PHONY: bluecheese

#
# local
#

bluecheese:
	cd bluecheese && lein run all local

ui:
	cd bluecheese && lein run ui local

auto-ui:
	cd bluecheese && lein auto run ui local

#
# prod
#

fe-deploy: pull-bluecheese run-bluecheese generate-parmesan commit-push-parmesan clean-up
	echo "Front-end deployed"

deploy: prod-init download-csv pull-bluecheese run-bluecheese generate-parmesan commit-push-parmesan clean-up
	echo "Deployed"

prod-init:
	mkdir -p data && mkdir -p bluecheese/resources/web/dist

download-csv:
	wget "https://covid.ourworldindata.org/data/owid-covid-data.csv" -O "data/owid-covid-data.csv"
	echo "downloaded: data/owid-covid-data.csv"

pull-bluecheese:
	git checkout master && \
		git branch -D deploy && \
		git pull && \
		git fetch origin deploy && \
		git checkout deploy && \
		echo "Git-pulled the deploy branch of bluecheese"

# TODO log
run-bluecheese:
	cd bluecheese && lein run all prod

generate-parmesan:
	cp -r bluecheese/resources/web/* ../parmesan
	echo "Parmesan generated"

commit-push-parmesan:
	cd ../parmesan && \
		git add . && \
		git commit -m "publish" && \
		git push && \
		echo "Parmesan pushed"

clean-up:
	# come back to master from deploy
	git checkout .
	git checkout master
