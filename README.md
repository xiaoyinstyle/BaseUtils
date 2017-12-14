# BaseUtils
### 说明
简单的快速开发框架，包含了 几个常用的activity、网络请求、图片加载、activity管理、以及常用工具类。东西不多基本够用

### 使用说明
#### 一、Activity的继承

 + NormalAcitivity
包含了简单的反调接口、4.4的沉浸式
+ 获取布局 ——> getViewByXml();
+ 初始化布局 ——> initView(Bundle bundle);
+ 初始化数据 ——> initData();

 + TitleActivity
 继承NormalAcitivity的带有标题栏， 标题(ll_title)属性有：
+ 左边返回（iv_left）
+ 左边文字（tv_left）
+ 右边返回（iv_right）
+ 右边文字（tv_right）
+ 标题文字（title）
 + WebViewActivity
 + TabActivity
 + RecyclerViewActivity