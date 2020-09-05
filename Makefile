.PHONY: test

#
# local
#

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

gorgonzola: all empty-gorgonzola serve-gorgonzola
	echo "Deployed"

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
