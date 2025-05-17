+++
date = "2022-08-03T22:30:00+02:00"
draft = false
title = "How I use MacBook without a mouse"
slug = 'no-mouse'
images = ["img/vimac.png"]

+++

I used to use one of the most expensive mice on the market,
[Logitech MX Revolution](https://www.cnet.com/reviews/logitech-mx-revolution-review/).
It was a mouse on which I could map around ten buttons. I enjoyed using it.

<img src="/img/1200px-Logitech_MX_Revolution_a.png" alt="Logitech MX Revolution" width="400px" />

I found it from a Stack Overflow thread discussing the best mouse for developers.
It was the second most popular answer; the most popular answer was "No mouse is the best mouse."

As usual, the thread was deleted, being flagged as off-topic.

I moved from the second-best mouse to the best mouse after I started using a Mac, and I rarely use trackpad when I work.

### Why no mouse?

I barely move my hands when I use my laptop, and I've never had wrist pain since ditching the mouse.
It's more productive and healthier.

I use these applications, tools, and tricks to avoid using the mouse.

### Chrome

#### Vimium

[Vimium](https://chrome.google.com/webstore/detail/vimium/dbepggeogbaibhgnhhndojpepiihcmeb?hl=en)
is a Chrome extension that enables you to navigate using keyboard shortcuts.

If you're not a vim user, you can learn some key mappings with it.

#### Switching focus from the address bar to the page

With Vimium, you can avoid using the mouse when the focus is inside a page, but you usually have to click the page to switch focus from the address bar to the page.

To avoid using the mouse, type in `javascript:` in the address bar and press Enter.

If you think `javascript:` is too long, you can add a shortcut by following the instructions on [this page](https://stackoverflow.com/a/30567119/524588).

### Vimac

[Vimac](https://vimacapp.com/)
is an open-source project that works similarly to Vimium.

It's a bit slow and flaky but useful.

<img src="/img/vimac.png" alt="vimac usage screenshot" />

### Shortcat

[Shortcat](https://shortcat.app/)
is similar to Vimac, but behaves slightly differently.
I stopped using Shortcat after I started using Vimac.

### BetterTouchTool (Paid)

[BetterTouchTool (BTT)](https://folivora.ai/)
is a tool that allows you to customize various input devices.

With BTT, I open applications and run shell scripts and AppleScripts using keyboard shortcuts.
A MacBook feels broken to me without it.

You can find more use cases in this Reddit post:
<iframe id="reddit-embed" src="https://www.redditmedia.com/r/MacOS/comments/nke8g6/bettertouchtool_is_one_of_the_most_worthit/?ref_source=embed&amp;ref=share&amp;embed=true&amp;showmedia=false" sandbox="allow-scripts allow-same-origin allow-popups" style="border: none;" height="162" width="640" scrolling="no"></iframe>

### Alfred (Paid for advanced features)

[Alfred](https://www.alfredapp.com/)
is an application launcher and productivity tool.

I use it to launch applications, search things, and run shell commands.

<img src="/img/alfred.png" alt="alfred usage screenshot" />

### Vim

Using Vim, you can avoid using the mouse while writing or coding.

Vim is not an easy application since it requires lots of time investment, but it's worth it if you plan to use your laptop for a long time.

If you haven’t started using Vim due to its steep learning curve, trying Vim plugins in your editor or IDE can be a good start.

Once you get used to Vim key mappings, you'll be surprised by how many applications support them.

If you're already using Vim and want to go to the next level, my previous article [Learnings after 500 commits to my vimrc](https://iamsang.com/en/2022/04/13/vimrc/) might be interesting to you.

### iTerm2, shell, and command line tools

Once you get used to shell scripts and command line tools, you can ditch many applications that require you to use the mouse.

For example, you can replace Postman with [Httpie](https://httpie.io/), and SourceTree with [tig](https://jonas.github.io/tig/).

### Hammerspoon

[Hammerspoon](https://www.hammerspoon.org/) is an application that allows users to customize and automate their Mac using the Lua programming language.

I use Hammerspoon mainly for these purposes:
* Automatically arranging windows
* Opening an application with a keyboard shortcut, specifically the last-used window of that application
* Toggling Do Not Disturb, Wi-Fi, and Bluetooth based on certain conditions

Hammerspoon isn't always stable, and the documentation is often unclear, but it provides powerful features that can't be easily replaced.

You can find my Hammerspoon configuration in [this GitHub repository](https://github.com/Sangdol/hammerspoon-config).

### AppleScripts

I don't like AppleScripts and never learned to write them properly, but it sometimes helps to automate things. The scripts I'll introduce here are written by someone else, and I tweaked them when needed.

I trigger these scripts with keyboard shortcuts using BetterTouchTool.

#### Un-minimize windows

Un-minimizing a window takes a few steps, and there's no built-in keyboard shortcut.

I use this script to un-minimize the lastly minimized window of the currently focused application ([gist](https://gist.github.com/Sangdol/bff520b39778e268aef45a7f592596aa)).

### JXA (JavaScript for Automation)

[JXA](https://developer.apple.com/library/archive/releasenotes/InterapplicationCommunication/RN-JavaScriptForAutomation/Articles/Introduction.html) is a JavaScript-based alternative to AppleScript. Apple introduced it long ago, but the documentation is horrible, so writing a decent script isn't easy.

Thankfully, a JavaScript library, [run-jxa](https://github.com/sindresorhus/run-jxa), allows you to run JXA from a node application.

#### Reminders JXA

[Reminders JXA](https://github.com/Sangdol/reminders-jxa) is a script that I wrote to use with Alfred. It doesn't have many features, but it does one thing well.

<img src="/img/alfred-reminders-jxa.png" alt="Alfred Reminder JXA usage screenshot" />

I used to use [an Alfred Reminders Workflow](https://github.com/surrealroad/alfred-reminders) to add new items to Reminders, but it was too laggy, so I decided to write my own Workflow.
