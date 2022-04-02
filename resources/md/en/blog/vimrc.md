+++
date = "2022-04-03T17:20:06+02:00"
draft = false
title = "Learnings after 500 commits to my vimrc"
slug = 'vimrc'
images = ["img/vimrc.png"]

+++

Summary
- I learned vim, vimscript and became very productive
- I wrote a plugin
- Knowing how to troubleshoot vim and plugins is key to use vim to the full force

Last winter on a Saturday, I decided to tweak my vimrc. I've been putting it off for a long time since I thought spending time on improving and cleaning up vimrc would be too luxury. It's fun but it wouldn't pay back for the time that I would invest.

<img src="/img/vimrc.png" alt="vimrc comic" />

But I determined to do me a favor and enjoying spending a day or two to work on my vimrc. It turned out that I spent three months making around 500 commits to my vimrc and I still work on it from time to time.

It was a fun and frustrating journey and I'd like to share what I've learned from the process and how happy I'm about it.

### What I've done

I started it with a light heart. I cleaned up some plugins that I don't need anymore. Then I cleaned up some configuration. And, I organized vimrc by splitting it into multiple files.

I decided to fix a few things that bothered me so I looked for a few plugins. Then I wanted to better understand things that are written in the help pages and plugin readme files so I started reading some vim books like [Learning the vi and Vim Editors, 8th Edition](https://www.oreilly.com/library/view/learning-the-vi/9781492078791/), [Practical Vim, 2nd Edition](https://learning.oreilly.com/library/view/practical-vim-2nd/9781680501629/), [Modern Vim](https://learning.oreilly.com/library/view/modern-vim/9781680506006/), [Mastering Vim](https://learning.oreilly.com/library/view/mastering-vim/9781789341096/), and [The VimL Primer](https://learning.oreilly.com/library/view/the-viml-primer/9781680500585/) (I was able to access all the books freely thanks to the O'Reilly membership that my employer gave me and I didn't need to feel guilty about skipping chapters).

I read a few awesome guys vimrc such as [the legendary junegunn](https://github.com/junegunn/dotfiles/blob/master/vimrc) who wrote [Plug](https://github.com/junegunn/vim-plug) and [fzf](https://github.com/junegunn/fzf.vim) and [another awesome guy wookayin](https://github.com/wookayin/dotfiles/) who is happen to be my previous coworker. I learned enormous amount of things from these materials but I still often had to read lots of help pages and SO threads to find out what I wanted to achieve.

I didn't mean to spend so much time on this but once I got started I couldn't stop. Understanding how things work and improving
my daily workflows with new knowledge was the most fun and exciting thing. I already used vim for around 10 years and I thought I use it quite effectively but the vim (and nvim) ecosystem has been grown a lot and there were tons of awesome things that I can utilize so it was much more beneficial then if I can use vim well years ago. Things are being developed rapidly and awesome plugins show up every week.

I remember I tried using vim as my IDE for Java but was frustrated due to a lack of features. I don't write Java these days but I use nvim for Scala, Clojure, Python, Lua, and vimscript. I'm a fan of IntelliJ but it feels too slow now after getting accustomed to vim.

### Learning vimscript

After learning vim for a while, I realized that vimscript is not that hard once I'm good at vim. So I decided to learn vimscript. When I start learning a programming language I start with a project that I can write tests with. This is called Test Driven Learning.

There's a testing vimscript testing framework [Vader](https://github.com/junegunn/vader.vim) which is written by Junegunn, the guy who wrote Plug and fzf. I created [a test driven learning project using Vader](https://github.com/Sangdol/vimscript-test-driven-learning).

Writing test codes is a good way to learn languages because of these reasons:
1. You learn the testing framework of the language in the early stage.
2. You learn by writing working code.
3. You have working documentation.

I was able to write [functions](https://github.com/Sangdol/vimrc/blob/master/vim/rc/functions.vim) to streamline my processes and fix or customize issues in plugins. I wrote a plugin [Mintabline](https://github.com/Sangdol/mintabline.vim/) to solve my own problem that neovim doesn't show the tabline properly for terminal buffers.

I feel much more powerful after learning vimscript.

### How to troubleshoot vim and plugins

I realized that knowing how to troubleshoot vim is key to utilize vim to the full power. I used to keep my plugin list lean to avoid randomly happening errors and slowness. I often didn't know where I should start to troubleshoot a problem. 

I tried using tens of plugins and faced loads of issues. It sometimes took weeks to find out the root cause of an issue. Some plugins conflict each other so I had to choose one over another.

Vim plugins are powerful since it can directly control the behavior of the editor within having to reside in a sandbox but due to this it could be incredible hard to debug an issue since it often doesn't tell any clue of the issue.

I learned a few tricks and I now can spot and fix issues quickly with these methods. 

#### Showing error messages with `:message`

The first thing we should do when we face an issue is to read error messages carefully. An error message quickly disappears when you do some actions. You can use the `:message` to bring the message back.

But it's not possible to copy it or have the message window open. To make it better, you can open a buffer with an output message using this function and command that I got from [this vim wiki page](https://vim.fandom.com/wiki/Append_output_of_an_external_command).

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

It's usually not easy to understand the root cause of an issue by just reading the error message.

When you don't have a clue you can do brute-force search by commenting out plugins, functions, or options. In which case, you can also do binary search which is drastically faster.

To make this task easier, I've modularized it and put together codes that do related tasks ([code link](https://github.com/Sangdol/vimrc/blob/master/vimrc)).

<img src="/img/vimrc-diagram.png" alt="vimrc diagram" />

With this structure, I can easily turn on and off a specific module. 

You need to follow this steps which could be annoying:

1. Disable modules or codes.
2. Run vim (in a separate terminal or window).
3. See if it's fixed.
4. Go to Step 1 if it's not fixed.

In the process, you can comment out half of the code at a time to effectively search the issue. 

This approach can be taken whenever we don't have any clue for an issue which is often the case with CSS.

<img src="/img/binary-search-in-practice-en.png" alt="binary search in practice comic" />

#### `git bisect` to find a problematic commit 

`git bisect` is another way to do binary search to pinpoint an issue. 

It works like this:

1. Start `git bisect`: `$ git bisect start`
2. Mark the current commit is bad: `$ git bisect bad`
3. Mark the good commit, say 100 commits ago it was good: `$ git bisect good HEAD~100`
4. Git starts bisecting. Run vim and see if the current commit it good.
5. Mark if the commit is good or bad: `$ git bisect (good|bad)`
6. Go to 4 until git pinpoints the problematic commit.
7. Finish `git bisect`: `$ git bisect reset`

It'll take up to 7 trials with over 100 commits (`math.log(100, 2) ≈ 6.65`).

#### Checking plugin documentation

You found out the plugin that has an issue but want to know why. 

You can first try:

1. Check out the vim help document of the plugin and see if it has relevant information. But, not all plugins have a help document. 
2. Visit the GitHub page of the plugin and see if there's any related open or closed issues.

If you're using Plug, you can easily open a plugin GitHub page with these functions. This will find a URL or a Plug command on the cursor line and open it in a browser.

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

#### (Advanced) checking out and customizing plugin code 

If you can't find any meaningful information from documentation, you can check out the plugin code. A vim package manager downloads plugin files into your machine so it's easy to explorer the files. 

I do this often so I have a mapping to find installed plugin files quickly with fzf:

```vim
nnoremap <leader>fpl :FZF ~/.vim/plugged<CR>
```

You can fix the issue directly with the downloaded files. I sometimes had to fix issues only for my environment. For example, I found out that the [vim-bbye](https://github.com/moll/vim-bbye) plugin and the [close-buffer](https://github.com/Asheq/close-buffers.vim) plugin conflict each other but I wanted to use both. I [forked vim-bbye](https://github.com/Sangdol/vim-bbye) and just deleted the conflicting code.

#### Searching for help

Lastly, you can look for help in a few places.

You can create a ticket or a discussion thread in the plugin repository. I once struggled with an issue with [Conjure](https://github.com/Olical/conjure). I thought asking and waiting for an answer would take too long but it turned out that it's a quicker way to solve the issue. Sometimes I find out the answer while writing a question.

Looking for a Discord community is another way. I use [coc-metals](https://github.com/scalameta/coc-metals) to write Scala and was able to get help from [the Discord server](https://discord.gg/mZkhURPznE). 

There are awesome plugin maintainers out there. 

### Improved Productivity 

I made a joke saying that I'll probably be able to reach a break-even point if I write code until 80 with the productivity gain given the time investment that I made. Probably that's not true but it's okay anyway since 1) I'll probably write code even after 80 and 2) I feel advanced as a software engineer.

I was able to experience improved productivity especially with these things.

#### File Manager with `fzf` and `nvim-tree`

I've tried file managers like [ranger](https://github.com/ranger/ranger) and [lf](https://github.com/gokcehan/lf) but I ended up not using them. 

I started using vim as a file manager with [fzf](https://github.com/junegunn/fzf.vim) and [nvim-tree](https://github.com/kyazdani42/nvim-tree.lua). This it the file manager that I've been looking for. I can move around tens of projects easily and searching for code quickly also by using fzf.

### nvim terminal as `tmux`

I'm obsessed with keyboard shortcuts. I've never seen anyone who uses more shortcuts than me (you can see how I manage my keyboard shortcuts in [my vimrc readme](https://github.com/Sangdol/vimrc/#mapping-tree)). 

I haven't been using `tmux` or `screen` since they have conflicting keyboard shortcuts with command line keyboard shortcuts. With nvim you can use terminals and I was able to customize mappings so that I can use all the keyboard shortcuts. Also, it's much easier to search, move around, and copy terminal outputs with nvim terminals.

### Git

I used to use [tig](https://github.com/jonas/tig) and the command line git client with lots of shell aliases and functions. 

I tried using [vim-fugitive](https://github.com/tpope/vim-fugitive) with [vim-rhubarb](https://github.com/tpope/vim-rhubarb), [git-messenger](https://github.com/rhysd/git-messenger.vim), [gv](https://github.com/junegunn/gv.vim), fzf (again), and [vim-signify](https://github.com/mhinz/vim-signify). It took a long time to set things up and get used to them but the old ways feel ancient now.
