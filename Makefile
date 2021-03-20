.PHONY: test

#
# local
#

# If you want to run a static local server,
# create articles (make article) and run a server in the `dist` directory.
local:
	lein ring server-headless 8080
	open http://localhost:8080

clean:
	rm -rf dist

test:
	lein test-refresh :changes-only

all: clean
	lein run all local

article:
	lein run article local

list:
	lein run list local

rss:
	lein run rss local

auto-ui:
	lein auto run copy-ui local

auto-all:
	lein auto run all local


#
# prod
#

gorgonzola: push prod empty-gorgonzola serve-gorgonzola
	echo "Deployed"

push:
	git push

prod: clean
	lein run all prod

empty-gorgonzola:
	-cd ../gorgonzola/ && \
		git rm -rf .

serve-gorgonzola:
	cp -r dist/* ../gorgonzola/ && \
		cd ../gorgonzola && \
		git add . && \
		git commit -m "publish" && \
		git push && \
		echo "Gorgonzola is ready."
