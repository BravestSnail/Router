# Router
KAPT实现的路由框架
## 功能
### Activity导航
![Router演示.gif](https://s2.loli.net/2022/10/24/pRncNtJCbhvMULy.gif)
## 用法
### 初始化
在合适的时机调用初始化方法, 建议在`Application.onCreate()`调用时机初始化
```kotlin
RouterUtils.init(Context)
```
`RouterInit`是KAPT自动生成，因此需要先运行一遍项目才会生成。
### 定义路由
```kotlin
@Router("/test")
class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }
}
```
使用`@Router`为Activity添加注解，并定义路由的URL。
### 路由跳转
```kotlin
RouterUtils.dispatch(this, "/test")
```
`dispatch()`需要一个Activity参数，和一个url参数，然后Router会自动寻找并启动相应的Activity
