# WeiBo
仿新浪微博 WeiBo

## MVP框架
什么是MVP？

MVP代表Model，View和Presenter。

* `View`层负责处理用户事件和视图部分的展示。在Android中，它可能是Activity或者Fragment类。
* `Model`层负责访问数据。数据可以是远端的Server API，本地数据库或者SharedPreference等。
* `Presenter`层是连接（或适配）View和Model的桥梁。

### MVC and MVP

MVC:`View`、 `Controller`and `Model`<br/>

![MVC](https://github.com/Sogrey/WeiBo/extra/20150608102446102.jpg)<br/>

MVP:`Model`、`View`和`Presenter`<br/>

![MVP](https://github.com/Sogrey/WeiBo/extra/20150608102447103.jpg)<br/>

在MVC框架中，View是可以直接读取Model模型中的数据的，Model模型数据发生改变是会通知View数据显示发生相应的改变。而在MVP中Model和View之间的没有任何联系，是两个完全独立的模块，当Model模型发生数据改变时，通过Presenter通知View视图发生相应的UI改变。

>Copyright (c) 2016 [Sogrey](https://github.com/Sogrey).<br/>
>IDE:Android Studio 2.1.1 + JDK 1.8<br/>
>QQ:408270653<br/>
>仅供学习交流<br/>
>Copyright (c) Sogrey, All rights reserved.
