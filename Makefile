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

fe-deploy: pull-bluecheese run-bluecheese generate-bleu-blog commit-push-bleu-blog clean-up
	echo "Front-end deployed"

deploy: prod-init pull-bluecheese run-bluecheese generate-bleu-blog commit-push-bleu-blog clean-up
	echo "Deployed"

prod-init:
	mkdir -p data && mkdir -p bluecheese/resources/web/dist

pull-bluecheese:
	git checkout master && \
		git branch -D deploy && \
		git pull && \
		git fetch origin deploy && \
		git checkout deploy && \
		echo "Git-pulled the deploy branch of bluecheese"

# TODO log
run-bluecheese:
	lein run all prod

generate-bleu-blog:
	cp -r bluecheese/resources/web/* ../bleu-blog
	echo "bleu-blog generated"

commit-push-bleu-blog:
	cd ../bleu-blog && \
		git add . && \
		git commit -m "publish" && \
		git push && \
		echo "bleu-blog pushed"

clean-up:
	# come back to master from deploy
	git checkout .
	git checkout master
