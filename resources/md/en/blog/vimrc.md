+++
date = "2022-04-13T21:55:00+02:00"
draft = false
title = "Learnings after 500 commits to my vimrc"
slug = 'vimrc'
images = ["img/vimrc-diagram.png"]

+++

Last winter on a Saturday, I decided to tweak [my vimrc](https://github.com/Sangdol/vimrc/). I'd been putting it off for a long time since I felt spending time on improving and cleaning up vimrc was too luxurious; it'd be fun but it wouldn't pay back the invested time.

<img src="/img/vimrc.png" alt="vimrc comic" />

This time I determined to do myself a favor and enjoy spending a day or two working on my vimrc.

I ended up spending more than three months and making around 500 commits to my vimrc, and I still work on it from time to time -- I didn't intend it but coincidentally it's exactly 500 commits at the time when I try to publish this article.

<img src="/img/500-commits-2.png" alt="500 commits" />

During the time, I've learned more than I'd learned for the past 10 years.

It was a fun and frustrating journey. I'd like to share what I've learned along the way and how happy I am now.

### Table of Contents

* [What I have done](#what-i-have-done)
  * [Books](#books)
  * [vimrcs](#vimrcs)
  * [vimscript](#vimscript)
* [How to troubleshoot vim and plugins](#how-to-troubleshoot-vim-and-plugins)
  * [Showing error messages with `:message`](#showing-error-messages-with-message)
  * [Binary search to find a problematic plugin or code](#binary-search-to-find-a-problematic-plugin-or-code)
  * [`git bisect` to find a problematic commit](#git-bisect-to-find-a-problematic-commit)
  * [Reading plugin documentation](#reading-plugin-documentation)
  * [(Advanced) customizing plugin code](#advanced-customizing-plugin-code)
  * [Searching for help](#searching-for-help)
* [Improved Productivity](#improved-productivity)
  * [Using vim as a File Manager with `fzf` and `nvim-tree`](#using-vim-as-a-file-manager-with-fzf-and-nvim-tree)
  * [nvim terminal as `tmux`](#nvim-terminal-as-tmux)
  * [Git](#git)
  * [Coding](#coding)
* [Reflection](#reflection)

### What I have done

I started by deleting unused plugins and configuration, then I looked for plugins to fix and improve a few things that were bothering me.

Once I got started working on it I couldn't stop. Learning how things work and improving my daily workflows with the new knowledge were so fun and exciting.

#### Books

To better understand vim help pages and plugin documents, I started checking out books.

* [Learning the vi and Vim Editors, 8th Edition](https://www.oreilly.com/library/view/learning-the-vi/9781492078791/),
* [Practical Vim, 2nd Edition](https://learning.oreilly.com/library/view/practical-vim-2nd/9781680501629/),
* [Modern Vim](https://learning.oreilly.com/library/view/modern-vim/9781680506006/),
* [Mastering Vim](https://learning.oreilly.com/library/view/mastering-vim/9781789341096/), and
* [The VimL Primer](https://learning.oreilly.com/library/view/the-viml-primer/9781680500585/).

Especially, _Learning the vi and Vim Editors_ was good to brush up on my vim skills, and _The VimL Primer_ was helpful to learn vimscript.

I was able to access all the books freely thanks to the [O'Reilly membership](https://www.oreilly.com/) that [my employer](https://jobs.zalando.com/de/) provides me.

I checked out chapters that interested me instead of reading cover to cover. Reading books with the membership feels like listening to music with a music streaming service instead of having to buy CDs.

#### vimrcs

I read a few awesome guys' vimrc such as

* [Junegunn](https://github.com/junegunn/dotfiles/blob/master/vimrc), who wrote [Plug](https://github.com/junegunn/vim-plug) and [fzf](https://github.com/junegunn/fzf.vim), and
* [wookayin](https://github.com/wookayin/dotfiles/), who wrote [gpustat](https://github.com/wookayin/gpustat) and happens to be my former co-worker.

I learned enormous amount of things from the books and vimrcs, but I still often had to read help pages and Stack Overflow threads to achieve what I want.

#### vimscript

I learned that Vimscript is not that hard once you're good with vim.

When I learn a new programming language I start writing tests with it. This is called Test-Driven Learning.

Writing test code is a good way to learn a programming language because
1. You learn the testing framework of a language in the early stage.
2. You learn by writing working code.
3. You have working documentation.

[Vader](https://github.com/junegunn/vader.vim) is a vimscript testing framework that is written by Junegunn. This is my Test-Driven Learning project using Vader: [Github project](https://github.com/Sangdol/vimscript-test-driven-learning).

I wrote a vim plugin [Mintabline](https://github.com/Sangdol/mintabline.vim/) to solve a problem that Neovim doesn't show the tabline properly for terminal buffers.

<img src="/img/mintablinev2.png" alt="mintabline screenshot" />

I feel powerful after learning vimscript.

### How to troubleshoot vim and plugins

I used to spend hours and days to find out the reason for a vim issue, but now It usually takes no more than 30 minutes using the methods that I'll introduce soon.

Knowing how to fix issues is the key to utilizing vim to its full power -- which is true for any other technologies.

I used to keep my plugin list lean to avoid randomly happening errors and slowness; I often didn't know where I should start to troubleshoot a problem.

For the past few months, I've tried tens of plugins and faced loads of issues; it sometimes took a few weeks to resolve.

Vim plugins are powerful since they can control vim without having to reside in a sandbox or container, but this makes it harder to debug an issue.

#### Showing error messages with `:message`

The first thing you should do when you face an issue is to read error messages carefully, but an error message could quickly disappear when you do some actions in vim. In this case, you can use the `:message` command to bring the message back.

However, it's not possible to easily copy the message or have the result window open while troubleshooting. To tackle this problem, you can load messages into a buffer using this function ([source wiki](https://vim.fandom.com/wiki/Append_output_of_an_external_command)).

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

You can use this function not only for messages but also for any other commands. For example, you will get a buffer list in a new buffer when you run this:

```vim
:TabMessage ls
```

#### Binary search to find a problematic plugin or code

You can try a manual binary search when it's not possible to find the root cause by just reading error messages, which is usually the case.

With this approach, you can search for problematic code by commenting out plugins, functions, or options -- half of the code at a time to make it efficient.

To make this task even easier, I've modularized my vimrc ([code link](https://github.com/Sangdol/vimrc/blob/master/vimrc)).

<img src="/img/vimrc-diagram.png" alt="vimrc diagram" />

With this structure, I can easily turn on and off a specific module.

These would be steps to debug:

1. Disable modules or code by deleting or commenting out.
2. Run vim (in a separate terminal or window).
3. See if it's fixed.
4. Go to Step 1 if it's not fixed.

This approach can be applied to any issues when there's no clue.

<img src="/img/binary-search-in-practice-en.png" alt="binary search in practice comic" />

#### `git bisect` to find a problematic commit

`git bisect` is another way to perform a binary search to pinpoint the cause of an issue.

It works like this:

```sh
# Start git bisect
$ git bisect start

# Tell Git that the current commit is bad
$ git bisect bad

# Say, 100 commits ago it was good
$ git bisect good HEAD~100

# Git starts bisecting.
# Run vim and see if the current commit is good
# and tell Git.
$ git bisect good

# Run this until git pinpoints
# the problematic commit.
$ git bisect bad

# Finish git bisect:
$ git bisect reset
```

It takes only 7 trials for 100 commits (`math.log(100, 2) ≈ 6.65`).

I prefer this approach to the previous one since it requires less manual work, although I have to check out [my Korean blog post about `git bisect`](https://iamsang.com/blog/2014/03/02/git-bisect/) whenever I run it to recall the commands for each step.

#### Reading plugin documentation

Surprisingly, reading documentation often works. You can first check out the vim help document of the plugin and see if it has relevant information.

If the plugin doesn't have a help document, visit the GitHub page of the plugin and see if there are any related issues.

To easily open the GitHub page of a Plug line in vimrc, I use a function and command similar to this:

```vim
function! s:open_plug_gh()
  let line = getline('.')
  let line = trim(line)
  let plug_regex = '\vPlug [''"](.{-})[''"].*'
  let path = substitute(line, plug_regex, '\1', '')
  let url = 'https://github.com/' .. path
  exec "!open '"..url.."'"
endfunction

nnoremap <Leader>op :call <SID>open_plug_gh()<CR><CR>
```

For example, if you run the function on the line below, vim will open the GitHub page of `fzf.vim` in the browser.
```vim
Plug 'junegunn/fzf.vim'
```

#### (Advanced) customizing plugin code

If you can't find any meaningful information from documentation, you can check out the code of the plugin. A vim package manager downloads plugin files into your machine so it's easy to explore the files.

I do this often so I have a mapping to find installed plugin files quickly with `fzf`.

```vim
nnoremap <leader>fpl :FZF ~/.vim/plugged<CR>
```

You can try fixing the issue directly in the downloaded files and see if it's fixed by reloading plugins or rerunning vim.

Sometimes I had to fix issues only for my environment. For example, the [vim-bbye](https://github.com/moll/vim-bbye) plugin and the [close-buffer](https://github.com/Asheq/close-buffers.vim) plugin conflict with each other but I wanted to use both. I just [forked vim-bbye](https://github.com/Sangdol/vim-bbye) and deleted the conflicting code.

#### Searching for help

Lastly, you can look for help in a few places.

* GitHub
* Discord
* Gitter
* Stack Overflow

I once struggled with an issue with [Conjure](https://github.com/Olical/conjure). I thought asking and waiting for an answer would take too long, but it turned out that it's a quicker way to solve the issue.

Looking for a Discord community is another way. I use [coc-metals](https://github.com/scalameta/coc-metals) for Scala and was able to get help from [the Discord server](https://discord.gg/mZkhURPznE).

There are awesome plugin maintainers out there.

Stack Overflow can be helpful as well. Once I even found out the answer to my issue while writing a question.

### Improved Productivity

I once made a joke saying that I'll reach a break-even point for the time investment that I made if I write code until 80. That's an exaggeration but it'll take a long time if I only calculate the saved seconds to recover the time investment.

The point is that [we automate things not to save time but to save mental energy](https://www.johndcook.com/blog/2015/12/22/automate-to-save-mental-energy-not-time/); I can try more things with the same time and energy, which reduces problem-solving time.

And, I'm going to write code until after 80 anyway.

#### Using vim as a File Manager with `fzf` and `nvim-tree`

I've tried file managers like [ranger](https://github.com/ranger/ranger) and [lf](https://github.com/gokcehan/lf) but I ended up not using them due to a few issues.

After I started using vim as a file manager with [fzf](https://github.com/junegunn/fzf.vim) and [nvim-tree](https://github.com/kyazdani42/nvim-tree.lua), I realized that this is the file manager that I've been looking for. I can move around and search for tens of projects easily.

<iframe id="reddit-embed" src="https://www.redditmedia.com/r/neovim/comments/ph7l41/such_a_powerful_tool/?ref_source=embed&amp;ref=share&amp;embed=true" sandbox="allow-scripts allow-same-origin allow-popups" style="border: none;" height="413" width="640" scrolling="no"></iframe>

#### nvim terminal as `tmux`

I haven't been using `tmux` or `screen` since they have conflicting keyboard shortcuts with command-line keyboard shortcuts.

When I needed to split a window and open a new session I could do it with iTerm2 shortcuts. This has downsides but worked okay for me.

With nvim you can use terminals with customized mappings. Also, it's much easier to search, move around, and copy terminal outputs within nvim terminals.

#### Git

I've been using [tig](https://github.com/jonas/tig) and the command-line git client with lots of shell aliases and functions.

This time I tried using [vim-fugitive](https://github.com/tpope/vim-fugitive) with [vim-rhubarb](https://github.com/tpope/vim-rhubarb), [git-messenger](https://github.com/rhysd/git-messenger.vim), [gv](https://github.com/junegunn/gv.vim), fzf (again), and [vim-signify](https://github.com/mhinz/vim-signify). It took a long time to set things up and get used to them but now the old ways feel ancient.

#### Coding

These are plugins that I use to write code.

* [coc](https://github.com/neoclide/coc.nvim) for language servers
* [coc-metals](https://github.com/scalameta/coc-metals) for Scala
* [coc-lua](https://github.com/josa42/coc-lua) for Lua
* [conjure](https://github.com/Olical/conjure) for Clojure
* [semshi](https://github.com/numirias/semshi) for Python
* [tree-sitter](https://github.com/nvim-treesitter/nvim-treesitter) for code highlights

I didn't have chances to write a large amount of code using vim after I set up these, but they are at least very useful for writing scripts and navigating codebases.

It wasn't always easy to set things up. I spent weeks to learn, custmoize, and troubleshoot when I started using `coc-metals` and `conjure`. I've faced a Lua version issue when I tried using `coc-lua`. Nothing was free but they were worth it.

I've put the plugins for coding in a separate vimrc module ([devplugins.vim](https://github.com/Sangdol/vimrc/blob/master/vim/rc/devplugins.vim)) so that I can exclude it in the server environment.

### Reflection

The most productive or successful people are not the ones who have the most productive development environment.

Once I started working on this, I didn't want to do anything else. Sometimes I was obsessed by small improvements that wouldn't affect my productivity after all. Still, I think I'm far from mastering vim and there are tons of things that I can try to tweak my productivity.

I learned a lot even from the effort that didn't work out, but it's important to be aware of the cost that I spend on improving productivity -- if I want to be productive.

<img src="/img/vimrc-40.png" alt="40 years" />
