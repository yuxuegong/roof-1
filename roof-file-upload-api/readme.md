# 说明

目录结构稍微进行了调整，把 layout 和 page 都合到了一个目录 `app/views` 里面。

以下划线开头的 `.scss` 文件不会被连接起来，而只是作为 `@import` 的目标使用，因此不要在里面实现真实样式。

所有产出的文件都在 `public` 目录下。

# quick start

使用 `linner w` 或者 `linner watch` 开始开发，使用 `linner b` 或者 `linner build` 打包工程。

使用 `jiggly` 来运行 watch 中或者 build 后的工程，以便使用浏览器访问。

JigglyPuff 的配置文件是工程根目录下的 `jiggly.json` ，Linner 的配置文件是工程根目录下的 `config.yml` 。


# Linner 安装

- 下载 [Ruby Installer](http://dl.bintray.com/oneclick/rubyinstaller/rubyinstaller-2.0.0-p481.exe), 并安装.
- 下载 [DevKit](http://cdn.rubyinstaller.org/archives/devkits/DevKit-mingw64-32-4.7.2-20130224-1151-sfx.exe), 解压后遵循以下步骤安装:
    1. `cd <DEVKIT_INSTALL_DIR>`
    2. `ruby dk.rb init`
    3. `ruby dk.rb install`
- 因为 `https://rubygems.org` 被墙了，所以我们要换成墙内的镜像
    ```bash
    # 查看现在的 source
    gem source
    # 移除一个 source
    gem source -r https://rubygems.org
    # 添加淘宝的镜像
    gem source -a http://ruby.taobao.org
    ```
- 执行 `gem install wdm` 来安装 linner 的一个依赖（仅限于 windows 操作系统）
- 执行 `gem install linner` 来安装开发工具.
- 执行 `linner help` 可以查看帮助

## 遇到 SSL 问题

- 下载 [证书](http://curl.haxx.se/ca/cacert.pem)
- 放到 ruby 目录下面例如 `C:/Ruby200/`
- 设置环境变量 `SET SSL_CERT_FILE=C:/Ruby200/cacert.pem`
- [SSL_connect returned=1 errno=0 state=SSLv3 read server certificate问题解决方案][1]

# JigglyPuff 安装

- 下载 [Node Installer](http://nodejs.org/dist/v0.10.31/x64/node-v0.10.31-x64.msi)，并安装。
- 执行 `npm install jigglypuff -g` 来安装 JigglyPuff

# Linner 的使用

* 安装 Ruby 2.0.0 以上版本

* 安装 Linner 及其使用规则

```bash
# 安装 Linner
gem install linner

# 使用 Linner 创建项目结构
linner new webapp && cd webapp

# 在项目下启动 Linner
linner watch

# 退出 Linner
CTRL + C

# 打包资源文件
linner build

# 清空打包的资源文件
linner clean
```

#### config.yml 文件配置详解

```yaml
paths:
  app: "app"
  test: "test"
  public: "public"
groups:
  scripts:
    paths:
      - app/scripts
    concat:
      "/scripts/app.js": "app/**/*.{js,coffee}"
      "/scripts/vendor.js": "vendor/**/*.{js,coffee}"
    order:
      - vendor/jquery-1.10.2.js
      - ...
      - app/scripts/app.coffee
  styles:
    paths:
      - app/styles
    concat:
      "/styles/app.css": "app/styles/**/[a-z]*.{css,scss,sass}"
  images:
    paths:
      - app/images
    sprite:
      "../app/images/icons.scss": "app/images/**/*.png"
  views:
    paths:
      - app/views
    copy:
      "/": "app/views/**/*.html"
  templates:
    paths:
      - app/templates
    precompile:
      "/scripts/templates.js": "app/templates/**/*.hbs"
modules:
  wrapper: cmd
  ignored: vendor/**/*
  definition: /scripts/app.js
sprites:
  url: /images/
  path: /images/
  selector: .icon-
revision: index.html
notification: true
bundles:
  jquery.js:
    version: 1.10.2
    url: http://code.jquery.com/jquery-1.10.2.js
  handlebars.js:
    version: 1.0.0
    url: https://raw.github.com/wycats/handlebars.js/1.0.0/dist/handlebars.runtime.js
```

`paths` 表示当前工具做监视的文件系统目录

`groups` 区分了不同的组，每个组可以有一个名字。在组内部的声明中需要指定当前组的
`paths`，然后可以指定一系列的操作原语，包括：`concat` `order` `copy`
`precompile` `sprite` 等

`modules` 定义了需要被 `CMD` 包装的文件路径，以及包装定义的头文件连接位置

`sprites` 定义了图片 `sprites` 的一些生成规则，例如以 `.icon-` 开头来生成 CSS
列表，这样用户便可以以这样的 CSS 选择器来直接生成样式。

`revision` 定义了需要被加载 `rev` 的文件，用以 md5 的文件名来替换旧文件名

`notification` 定义了是否需要有通知系统，（用以 Mac 系统的通知）

`bundles` 定义了项目的依赖关系，项目可以依赖很多第三方的项目，可以自定义版本号。
如果需要每次启动都更新最新版本的依赖，可以将 `version` 设置为 `master`

## 工具阐述

简单来说，Linner 允许我们做：

1. 项目结构化
2. 页面组件化
3. 仓库管理
4. 使用 CoffeeScript 与 SCSS 来替代 JavaScript 与 CSS
5. 合并 CSS 与 JavaScript 文件
6. 复制 CSS 与 JavaScript 文件
7. 预编译 CSS 与 JavaScript 文件
8. 合并图片至一张大图（Sprites）
9. 压缩 CSS 与 JavaScript 文件
10. 监视文件系统变化并实时更新

#### 1. 项目结构化

项目通过标准化的方式来组织：

```bash
.
├── app
│   ├── components
│   │   └── dropdown
│   │       ├── view.coffee
│   │       ├── view.hbs
│   │       └── view.scss
│   ├── images
│   │   └── logo.png
│   ├── scripts
│   │   └── app.coffee
│   ├── styles
│   │   └── app.scss
│   ├── templates
│   │   └── welcome.hbs
│   └── views
│       └── index.html
├── bin
│   └── server
├── config.yml
├── public
├── test
└── vendor
```
`app` 文件夹是用户自己编写代码的地方
  * `images` 用以存放项目相关的图片文件
  * `scripts` 用以存放项目相关的 JavaScript 文件
  * `styles` 用以存放项目相关的 Stylesheet 文件
  * `templates` 用以存放项目相关的前端模板文件
  * `views` 用以存放项目相关的后端模板文件
  * `components` 用以存放项目的组件文件

`config.yml` 是整个项目的配置文件

`bin` 文件夹可以让用户很方便的启动一个本地的服务器，以当前文件夹作为根

`test` 文件夹可以使用户编写一些单元测试来测试自己的前端项目

`vendor` 文件夹可以使用户引入第三方的代码组件，如 jQuery、Underscore 等

`public` 文件夹是项目打包后文件位置，发布项目所需要的所有文件

#### 2. 页面组件化

> 不要面向页面编程，要面向组件编程。

当拿到网站的整体设计稿时，我们应该首先去找出页面间有相同逻辑的模块。
将他们抽出，考虑如何将其设计为可复用的模块。

我们可以将模块的内容包含 JavaScript CSS 与前后端模板组织在 `app/components`
内，通过在 `app/scripts` 里面的 `app.coffee` 中去初始化所有的组件。

通过 `cmd` 的形式去管理组件与组件之间的依赖关系。这样组件内部就可以通过
`module.exports = "dropdown"` 与 `require "dropdown"` 这样的形式去导出与依赖组件。

#### 3. 仓库管理

通过 `bundles` 来管理远端依赖，在项目中可以非常方便的引入一些著名的第三方库。
如 jQuery，Underscore 等。

依赖可以规定多个版本，当需要升级版本时，可以更改 `version` 与 `url`
在工具下次启动时便可以拉取新版本的依赖。如果希望依赖一直为最新状态，可以将 `version` 设置为 `master` 这样工具会在每次启动时都获取最新的文件。

#### 4. 使用 CoffeeScript 与 SCSS 来替代 JavaScript 与 CSS

##### CoffeeScript

CoffeeScript 是这一门编程语言构建在 JavaScript 之上，其被编译成高效的 JavaScript，
这样你就可以在 Web 浏览器上运行它，或是通过诸如用于服务器端应用的 Node.js 一类的技术来使用它。
编译过程通常都很简单，产生出来的 JavaScript 与许多的最佳做法都保持了一致。

##### SCSS

SCSS 扩展了 CSS3，增加了规则、变量、混入、选择器、继承等等特性。
SCSS 生成良好格式化的 CSS 代码，易于组织和维护。

##### 使用成本

使用 CoffeeScript 与 SCSS 可以大幅降低开发成本，使用 CoffeeScript 可以避免一些常见的 JavaScript 开发错误。
而 SCSS 则可以更好的抽象样式文件，使样式得到更好的维护。并且 CoffeeScript 与 SCSS 的学习成本都很低，
前端可以通过很简短的学习就能立刻写出优雅的代码。

#### 5. 合并 CSS 与 JavaScript 文件

前端文件的合并可以明显的减少 HTTP 请求，明显的加快网页的浏览速度。

#### 6. 复制 CSS 与 JavaScript 文件

复制文件是一个很普遍的需求，可以将文件从一个位置复制到另一个位置。如果是 CoffeeScript 文件或者 SCSS 文件，工具会帮助转换为对应的 Javascript 与 CSS 文件。

#### 7. 预编译 JavaScript 模板文件 

随着互联网应用越来越大，前端模板的需求也日渐突出。在使用前端模板的过程中，
为了提高前端的渲染性能，我们需要对前端模板进行预编译。预编译的结果是使 `templates`
文件能直接转化为 JavaScript 的方法调用。这样可以以非常快的速度来渲染前端模板。

#### 8. 合并图片至一张大图（Sprites）

当页面内的图片很多时，会产生多个 HTTP 请求，当请求变多时会严重影响网站的速度。
所以我们需要将多个 PNG 图片合并成一张图片。同时利用 CSS 的 `background`
来显示对应的单个图片

#### 9. 压缩 CSS 与 JavaScript 文件

在项目发布上线时需要将资源文件进行最大程度的压缩。从而减少 HTTP 请求文件的体积。
从而可以将文件尽快的传输给用户，使页面更快的展示出来。

工具提供了快速的文件名的版本替换，可以使服务器更好的缓存压缩后的文件。

#### 10. 监视文件系统变化并实时更新

文件系统的实时监控可以监控到项目内文件的变动，同时重新执行整个工具的逻辑。

在开发阶段，我们可以尽最大程度的提高开发者的效率。例如使用浏览器实时刷新。
当文件系统有任何变化时，工具会发动 LiveReload 来自动刷新页面，
当用户只修改了 CSS 文件时，我们甚至可以不刷新页面，直接重载 CSS 文件。
极大的提高开发效率。


##其他说明

###前端模板的使用方式

需要再templates目录下定义相应的模板，如welcome.hbs
```js
template = Handlebars.templates["welcome"]
    $template = $(template({name:"lisi"}))
    $("body").append  $template
```

###JigglyPuff 的配置文件详解
配置文件为`jiggly.json`

```js
{
  "filesHome": "public",
  "dataFile": "test/data.js", //mock数据的数据文件位置
  "serverPort": 3000,
  "pageMode": true,
  "extraHelpers":["test/custHelper.js"] //handlebars的自定义helper类的文件位置

}

```


  [1]: http://www.cnblogs.com/sunada2005/p/3357201.html