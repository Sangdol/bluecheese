+++
date = "2022-04-03T17:20:06+02:00"
draft = false
title = "Learnings after 500 commits to my vimrc"
slug = 'vimrc'
images = ["img/vimrc.png"]

+++

Last winter on a Saturday, I decided to tweak my vimrc. I've been putting it off for a long time since I felt spending time on improving and cleaning up vimrc too luxurious. I thought it's fun but it wouldn't pay back the time that I invest.

<img src="/img/vimrc.png" alt="vimrc comic" />

But, I determined to do me a favor and enjoy spending a day or two working on [my vimrc](https://github.com/Sangdol/vimrc/). I ended up spending more than three months making around 500 commits to my vimrc and I still work on it from time to time.

It was a fun and frustrating journey and I'd like to share what I've learned along the way and how happy I am now.

[TOC]

### What I have done

I started by deleting unused plugins and configuration. I didn't mean to spend so much time on this.

But, once I got started I couldn't stop. Understanding how things work and improving my daily workflows with new knowledge was so fun and exciting.

#### Books

I decided to fix and improve a few things that were bothering me so I looked for plugins. Then I wanted to understand help pages and plugin READMEs so I started checking out books like

* [Learning the vi and Vim Editors, 8th Edition](https://www.oreilly.com/library/view/learning-the-vi/9781492078791/),
* [Practical Vim, 2nd Edition](https://learning.oreilly.com/library/view/practical-vim-2nd/9781680501629/),
* [Modern Vim](https://learning.oreilly.com/library/view/modern-vim/9781680506006/),
* [Mastering Vim](https://learning.oreilly.com/library/view/mastering-vim/9781789341096/), and
* [The VimL Primer](https://learning.oreilly.com/library/view/the-viml-primer/9781680500585/).

_Learning the vi and Vim Editors, 8th Edition_ was good to brush up on my vim skills and _The VimL Primer_ was easy and nice to read to learn vimscript.

I was able to access all the books freely thanks to the [O'Reilly membership](https://www.oreilly.com/) that [my employer](https://jobs.zalando.com/de/) provided me. I read chapters that explains what I want to learn intead of reading them from the beginning. Reading books with the membership is so awesome that it feels like using a music streaming service instead of buying CDs.

#### vimrcs

I read a few awesome guys vimrcs such as

* [Junegunn](https://github.com/junegunn/dotfiles/blob/master/vimrc), who wrote [Plug](https://github.com/junegunn/vim-plug) and [fzf](https://github.com/junegunn/fzf.vim), and
* [wookayin](https://github.com/wookayin/dotfiles/), who wrote [gpustat](https://github.com/wookayin/gpustat) and happens to be my former co-worker.

I learned enormous amount of things from books and vimrcs but I still often had to read lots of help pages and SO threads to achieve what I want.

#### vimscript

I realized that vimscript is not that hard once you're good with vim.

When I learn a new programming language I start writing tests with it. This is called Test Driven Learning.

Writing test codes is a good way to learn a programming language because
1. You learn the testing framework of the language in the early stage.
2. You learn by writing working code.
3. You have working documentation.

[Vader](https://github.com/junegunn/vader.vim) is a vimscript testing framework which is written by Junegunn (again). This is my learning project using Vader ([Github project](https://github.com/Sangdol/vimscript-test-driven-learning)).

With the knowledge I wrote [functions for my vimrc](https://github.com/Sangdol/vimrc/blob/master/vim/rc/functions.vim) and customized plugins.

I also wrote a vim plugin [Mintabline](https://github.com/Sangdol/mintabline.vim/) to solve my problem that neovim doesn't show the tabline properly for terminal buffers.

I feel much more powerful after learning vimscript.

### How to troubleshoot vim and plugins

I used to spend hours and days or even weeks to find out the reason of an issue but now It takes no more than 30 minutes after I learned the things that I'll introduce here.

Knowing how to fix vim issues is the key to utilize vim to the full power. I used to keep my plugin list lean to avoid randomly happening errors and slowness. I often didn't know where I should start to troubleshoot a problem.

For the past few months, I've tried tens of plugins and faced loads of issues. It sometimes took a few week to resolve an issue.

Vim plugins are powerful since it can directly control vim without having to reside in a sandbox or a container but due to this it could be incredibly hard to debug an issue since a problem often doesn't provide any clue.

#### Showing error messages with `:message`

The first thing you should do when you face an issue is to read error messages carefully. But an error message could quickly disappear when you do some actions. You can use the `:message` command to bring the message back.

But, it's not possible to easily copy the message or have it open while troubleshooting. With this function, you can load messages into a buffer ([source wiki](https://vim.fandom.com/wiki/Append_output_of_an_external_command)).

```vim
function! TabMessage(cmd)
  redir => message
  silent execute a:cmd
  redir END
  if empty(message)
    echoerr "no output"
  else
    tabnew
    setlocal buftype=nofile bufhidden=wipe noswapfile nobuflisted nomodified
    silent put=message
  endif
endfunction

command! -nargs=+ -complete=command TabMessage call TabMessage(<q-args>)
```

Once you have the code in your vimrc you can open a buffer with messages like this:

```vim
:TabMessage message
```

or simply
```vim
:TabMessage mes
```

#### Binary search to find a problematic plugin or code

You can try manual binary search when it's not possible to find the root cause by just reading error messages which is usually the case.

With this approach you search problematic code by commenting out plugins, functions, or options. In the process, you can comment out half of the code at a time to make it efficient.

To make this task even more easier, I've modularized my vimrc ([code link](https://github.com/Sangdol/vimrc/blob/master/vimrc)).

<img src="/img/vimrc-diagram.png" alt="vimrc diagram" />

With this structure, I can easily turn on and off a specific module.

This would be steps to debug:

1. Disable modules or codes by deleting or commenting them.
2. Run vim (in a separate terminal or window).
3. See if it's fixed.
4. Go to Step 1 if it's not fixed.

You can take this approach whenever there's no clue which is often the case with CSS.

<img src="/img/binary-search-in-practice-en.png" alt="binary search in practice comic" />

#### `git bisect` to find a problematic commit

`git bisect` is another way to do binary search to pinpoint the cause of an issue.

It works like this:

```sh
# Start `git bisect`
$ git bisect start

# Mark the current commit is bad
$ git bisect bad

# Say 100 commits ago it was good
$ git bisect good HEAD~100

# Git starts bisecting.
# Run vim and see if the current commit it good.
$ git bisect good

# Run this until git pinpoints the problematic commit.
$ git bisect bad

# Finish `git bisect`:
$ git bisect reset
```

It takes only 7 trials for 100 commits (`math.log(100, 2) ≈ 6.65`).

#### Reading plugin documentation

You found out the plugin that is causing an issue but want to know why.

You can first try:

1. Check out the vim help document of the plugin and see if it has relevant information. But, not all plugins have a help document.
2. Visit the GitHub page of the plugin and see if there's any related issues.

I use these functions to easily open a GitHub page of a Plug line.

```vim
function! s:open_url(url)
  echom 'Opening "' .. a:url .. '"'
  if !empty(a:url)
    if has("mac")
      exec "!open '"..a:url.."'"
    elseif has("unix")
      exec "!google-chrome '"..a:url.."'"
    endif
  else
    echom "No URL found"
  endif
endfunction

function! s:browser()
  let line = getline('.')
  let line = trim(line)
  let is_plug = StartsWith(line, 'Plug')
  if is_plug
    let path = substitute(line, '\vPlug [''"](.{-})[''"].*', '\1', '')
    let url = 'https://github.com/' .. path
    call s:open_url(url)
  else
    " The regex for URLs is customized for my use cases. "
    let line = matchstr(line, "http[^ `)]*")
    let url = escape(line, "#;|%")
    call s:open_url(url)
  endif
endfunction

nnoremap <Leader>b :call <SID>browser()<CR><CR>
```

For example, if you run the `s:browser()` function on this line, it'll open the GitHub page of the plugin in the browser.
```vim
Plug 'junegunn/fzf.vim'
```

#### (Advanced) customizing plugin code

If you can't find any meaningful information from documentation, you can check out the plugin code. A vim package manager downloads plugin files into your machine so it's easy to explorer the files.

I do this often so I have a mapping to find installed plugin files quickly with fzf:

```vim
nnoremap <leader>fpl :FZF ~/.vim/plugged<CR>
```

You can try fixing the issue directly in the downloaded files and see if it's fixed by reloading plugins or rerunning vim.

I sometimes had to fix issues only for my environment. For example, I found out that the [vim-bbye](https://github.com/moll/vim-bbye) plugin and the [close-buffer](https://github.com/Asheq/close-buffers.vim) plugin conflict each other but I wanted to use both. I [forked vim-bbye](https://github.com/Sangdol/vim-bbye) and just deleted the conflicting code.

#### Searching for help

Lastly, you can look for help in a few places such as

* GitHub
* Discord
* Gitter
* Stack Overflow

I once struggled with an issue with [Conjure](https://github.com/Olical/conjure). I thought asking and waiting for an answer would take too long but it turned out that it's a quicker way to solve the issue.

Looking for a Discord community is another way. I use [coc-metals](https://github.com/scalameta/coc-metals) to write Scala and was able to get help from [the Discord server](https://discord.gg/mZkhURPznE). I don't

There are awesome plugin maintainers out there.

Stack Overflow can be helpful as well. Once I even found out the answer to my issue while writing a question.

### Improved Productivity

I once made a joke saying that I'll probably be able to reach a break-even point for the time investment that I made if I write code until 80.

Probably that's not true but it's okay in any case since 1) I'll probably write code even after 80 and 2) I already feel advanced as a software engineer.

#### Using vim as a File Manager with `fzf` and `nvim-tree`

I've tried file managers like [ranger](https://github.com/ranger/ranger) and [lf](https://github.com/gokcehan/lf) but I ended up not using them due to small issues.

After I started using vim as a file manager with [fzf](https://github.com/junegunn/fzf.vim) and [nvim-tree](https://github.com/kyazdani42/nvim-tree.lua), I realized that this it the file manager that I've been looking for. I can move around and search tens of projects easily.

<iframe id="reddit-embed" src="https://www.redditmedia.com/r/neovim/comments/ph7l41/such_a_powerful_tool/?ref_source=embed&amp;ref=share&amp;embed=true" sandbox="allow-scripts allow-same-origin allow-popups" style="border: none;" height="413" width="640" scrolling="no"></iframe>

#### nvim terminal as `tmux`

I haven't been using `tmux` or `screen` since they have conflicting keyboard shortcuts with command line keyboard shortcuts.

When I needed to split a window and open a new session I could do it with iTerm2 shortcuts. This has downsides but worked okay for me.

With nvim you can use terminals with customized mappings. Also, it's much easier to search, move around, and copy terminal outputs with within nvim terminals.

#### Git

I've been using [tig](https://github.com/jonas/tig) and the command line git client with lots of shell aliases and functions.

This time I tried using [vim-fugitive](https://github.com/tpope/vim-fugitive) with [vim-rhubarb](https://github.com/tpope/vim-rhubarb), [git-messenger](https://github.com/rhysd/git-messenger.vim), [gv](https://github.com/junegunn/gv.vim), fzf (again), and [vim-signify](https://github.com/mhinz/vim-signify). It took a long time to set things up and get used to them but now the old ways feel ancient.

### Next Steps

A long time ago I tried using vim as an IDE for Java but was failed due to a lack of features. I don't write Java these days but I use nvim for Scala, Clojure, Python, Lua, and vimscript. I'm a fan of IntelliJ but it feels too slow after getting used to nvim.

A few months ago I found [tree-sitter](https://github.com/tree-sitter/tree-sitter) but didn’t use it since it was at the development stage and I had to build it myself to use it. When I checked it out again a few weeks ago, it was ready to be used and plugins are developed using tree-sitter such as [dim](https://github.com/narutoxy/dim.lua).

Things are being developed rapidly and awesome plugins continuously show up.

[Truning vim into an IDE is not the goal of Neovim](https://neovim.io/charter/) but it is quickly evolving into the best IDE for vim users.
