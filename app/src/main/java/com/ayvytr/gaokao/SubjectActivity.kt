package com.ayvytr.gaokao

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.ayvytr.commonlibrary.bean.AppSubject
import com.ayvytr.commonlibrary.constant.IntentConst
import com.ayvytr.commonlibrary.constant.WebConstant
import com.ayvytr.mvp.IPresenter
import com.ayvytr.network.ApiClient
import com.ayvytr.rxlifecycle.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_subject_detail.*
import ru.noties.markwon.AbstractMarkwonPlugin
import ru.noties.markwon.Markwon
import ru.noties.markwon.MarkwonConfiguration
import ru.noties.markwon.ext.tables.TablePlugin
import ru.noties.markwon.html.MarkwonHtmlParserImpl
import ru.noties.markwon.image.AsyncDrawableLoader
import ru.noties.markwon.image.ImagesPlugin
import ru.noties.markwon.image.gif.GifPlugin
import ru.noties.markwon.image.network.NetworkSchemeHandler
import ru.noties.markwon.image.okhttp.OkHttpImagesPlugin


class SubjectActivity : BaseMvpActivity<IPresenter>() {
    private lateinit var mAppSubject: AppSubject

    override fun initExtra() {
        mAppSubject = intent.getParcelableExtra<AppSubject>(IntentConst.EXTRA_SUBJECT)
    }

    override fun initView(savedInstanceState: Bundle?) {
        val markwon = Markwon.builder(getContext())
            .usePlugin(object : AbstractMarkwonPlugin() {
                override fun configureConfiguration(builder: MarkwonConfiguration.Builder) {
                    builder.htmlParser(MarkwonHtmlParserImpl.create())
                    builder.linkResolver { view, link ->
                        ARouter.getInstance().build(WebConstant.WEB)
                            .withString(WebConstant.EXTRA_URL, link)
                            .navigation(getContext())
                    }
                }

                override fun configureImages(builder: AsyncDrawableLoader.Builder) {
                    builder.addSchemeHandler("http", NetworkSchemeHandler())
                    builder.errorDrawableProvider {
                        getContext().resources.getDrawable(R.drawable.photo_error)
                    }
                    builder.placeholderDrawableProvider {
                        getContext().resources.getDrawable(R.drawable.photo_loading)
                    }
                }
            })
            .usePlugin(TablePlugin.create(getContext()))
            .usePlugin(ImagesPlugin.create(getContext()))
            .usePlugin(OkHttpImagesPlugin.create(ApiClient.getInstance().okHttpClient))
            .usePlugin(GifPlugin.create())
            .build()

        markwon.setMarkdown(
            tv_content, "# Android内存优化\n" +
                    "![图片](https://raw.githubusercontent.com/zzhoujay/RichText/master/image/image.jpg)\n" +
                    "# 分隔\n\n" +
                    "![Gif](https://raw.githubusercontent.com/Carbs0126/Screenshot/master/numberpickerview.gif)\n" +
                    "\n" +
                    "## 常见内存泄漏场景\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "### 1. 需要手动关闭的对象没有关闭\n" +
                    "\n" +
                    "#### 1.1. try/catch/finally中网络文件等流的手动关闭\n" +
                    "\n" +
                    "- HTTP\n" +
                    "- File\n" +
                    "- ContendProvider\n" +
                    "- Bitmap\n" +
                    "- Uri\n" +
                    "- Socket\n" +
                    "\n" +
                    "这些都是java基础啦，就不一一介绍了。我们可以用RxJava进行封装，让它变成可观察的流；在Go语言中，可以使用Defer这样的方法来减少迷之缩进；在okhttp中，使用了[引用计数](https://link.juejin.im/?target=http%3A%2F%2Fwww.jianshu.com%2Fp%2F92a61357164b)的技术对流进行管理\n" +
                    "\n" +
                    "#### 1.2. `onDestroy()` 或者 `onPause()`中未及时关闭对象\n" +
                    "\n" +
                    "泄露实例：\n" +
                    "\n" +
                    "- 线程泄漏：当你执行耗时任务，在`onDestroy()`的时候考虑调用`Thread.close()`，如果对线程的控制不够强的话，可以使用RxJava自动建立线程池进行控制，并在生命周期结束时取消订阅；\n" +
                    "- Handler泄露：当退出activity时，要注意所在Handler消息队列中的Message是否全部处理完成，可以考虑`removeCallbacksAndMessages(null)`手动关闭\n" +
                    "- 广播泄露：手动注册广播时，记住退出的时候要`unregisterReceiver()`\n" +
                    "- 第三方SDK/开源框架泄露：ShareSDK, JPush等第三方SDK需要按照文档控制生命周期,它们有时候要求你继承它们丑陋的activity，其实也为了帮你控制生命周期\n" +
                    "- 各种callBack/Listener的泄露，要及时设置为Null，特别是static的callback\n" +
                    "- EventBus等观察者模式的框架需要手动解除注册\n" +
                    "- 某些Service也要及时关闭，比如图片上传，当上传成功后，要`stopself()`\n" +
                    "- Webview需要手动调用`WebView.onPause()`以及`WebView.destory()`\n" +
                    "\n" +
                    "比如常见的ButterKnife\n" +
                    "\n" +
                    "```\n" +
                    "  @Override public void onDestroyView() {\n" +
                    "    super.onDestroyView();\n" +
                    "    ButterKnife.reset(this);\n" +
                    "  }\n" +
                    "```\n" +
                    "\n" +
                    "再比如ShareSDK（此垃圾再也不用）\n" +
                    "\n" +
                    "```\n" +
                    "protected void onDestroy() {\n" +
                    "        ShareSDK.stopSDK(this);\n" +
                    "        super.onDestroy();\n" +
                    "    }\n" +
                    "```\n" +
                    "\n" +
                    "> 使用开源的框架（比如帮你写好的图片下载队列，REST解析等）可能会帮助你快速的解决这个问题，但是知其然并知其所以然，也要了解它们的生命周期\n" +
                    "\n" +
                    "### 2. Static的使用\n" +
                    "\n" +
                    "#### 2.1 static class/method/variable 的区别，你真的懂了吗？\n" +
                    "\n" +
                    "(1). **Static inner class 与 non static inner class 的区别**\n" +
                    "\n" +
                    "static inner class 即`静态内部类`，它只会出现在类的内部，在某个类中写一个静态内部类其实同你在IDE里新建一个`.java` 文件是完全一样的。\n" +
                    "\n" +
                    "以下为它们的对比\n" +
                    "\n" +
                    "| class对比                       | static inner class             | non-static inner class               |\n" +
                    "| ------------------------------- | ------------------------------ | ------------------------------------ |\n" +
                    "| 与外部class引用关系             | 如果没有传入参数，就无引用关系 | 自动获得强引用（implicit reference） |\n" +
                    "| 被调用时需要外部实例            | 不需要（比如Bulider类）        | 需要                                 |\n" +
                    "| 能否调用外部class中的变量与方法 | 不能                           | 能                                   |\n" +
                    "| 生命周期                        | 自主的生命周期                 | 依赖于外部类，甚至比外部类更长       |\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "可以看到，在生命周期中，埋下了内存泄漏的隐患，如果它的生命周期比activity更长，那么可能会发生泄露，更可怕的是，有可能会产生难以预防的空指针问题。\n" +
                    "\n" +
                    "这个泄露的例子，详见内存管理(2)的文章。\n" +
                    "\n" +
                    "(2). static inner method\n" +
                    "\n" +
                    "静态内部方法，也就是虚函数：可以被直接调用，而不用去依赖它所在的类，比如你需要随机数，只用调用`Math.random()`即可，而不用实例化`Math`这个对象。在工具类（Utils）中，建议用static修饰方法。static方法的调用不会泄露内存。\n" +
                    "\n" +
                    "(3). static inner variable\n" +
                    "\n" +
                    "慎重使用静态变量，静态变量是被分配给当前的Class的，而不是一个独立的实例，当ClassLoader停止加载这个Class时，它才会回收。在Android中，需要手动置空才会卸掉ClassLoader，才能出现GC。\n" +
                    "\n" +
                    "> static 变量称为静态变量或者类变量，它由类的所有实例共享。\n" +
                    "> Classes are only unloaded if all classes associated with a ClassLoader can be garbage collected, which is rare but will not be impossible in Android.\n" +
                    "> 高效的场景：“全局常量”，“单例”与“远程接口”。\n" +
                    "\n" +
                    "这段谷歌博客上的著名代码演示了一次内存泄露的，当你旋转屏幕后，Drawable就会泄露。\n" +
                    "\n" +
                    "```\n" +
                    "private static Drawable sBackground;\n" +
                    "\n" +
                    "@Override\n" +
                    "protected void onCreate(Bundle state) {\n" +
                    "  super.onCreate(state);\n" +
                    "\n" +
                    "  TextView label = new TextView(this);\n" +
                    "  label.setText(\"Leaks are bad\");\n" +
                    "\n" +
                    "  if (sBackground == null) {\n" +
                    "    sBackground = getDrawable(R.drawable.large_bitmap);\n" +
                    "  }\n" +
                    "  label.setBackgroundDrawable(sBackground);\n" +
                    "\n" +
                    "  setContentView(label);\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "注意，我在实际试验(CM12)中，没有GC时，导出的堆是有泄露的，而手动GC后，是**不会发生**内存泄露的，希望各位自己做实验，发一下反馈（评论已经有了相关反馈）。\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "MAT\n" +
                    "\n" +
                    "总的来说，还是少用，就算可能是新的设备支持更加先进的GC，但是还是要注意控制内存。\n" +
                    "\n" +
                    "#### 2.2. 使用内部匿名类要注意什么？\n" +
                    "\n" +
                    "匿名内部类实际上就是non-static inner class，比如某些初学者经常一个`new Handler`就写出来了，它对外部类有一个强引用。建议单独写出来这个类并继承，并加入static修饰。\n" +
                    "\n" +
                    "#### 2.3. 单例模式（Singleton）是不是内存泄漏？\n" +
                    "\n" +
                    "在单例模式中，只有一个对象被产生，看起来一直占用了内存，但是这个**不意味**就是浪费了内存，内存本来就是用来装东西的，只要这个对象一直被高效的利用就不能叫做泄露。但是也不要偷懒，一个劲的全整成了单例，越多的单例会让内存占用过多，放在Application中初始化的内容也越多，意味着APP打开白屏的时间会更久，而且软件维护起来也变得复杂。\n" +
                    "\n" +
                    "- 好的例子：GlobalContext，SmsReceiver动态注册，EventBus\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "可以看到我们在 Activity 中继承 AsyncTask 自定义了一个非静态内部类，在 doInbackground() 方法中做了耗时的操作，然后在 onCreate() 中启动 MyAsyncTask。如果在耗时操作结束之前，Activity 被销毁了，这时候因为 MyAsyncTask 持有 Activity 的强引用，便会导致 Activity 的内存无法被回收，这时候便会产生内存泄露。\n" +
                    "\n" +
                    "public class MainActivity extends AppCompatActivity {\n" +
                    "\n" +
                    "```java\n" +
                    "@Override\n" +
                    "protected void onCreate(Bundle savedInstanceState) {\n" +
                    "    super.onCreate(savedInstanceState);\n" +
                    "    setContentView(R.layout.activity_main);\n" +
                    "    new MyAscnyTask().execute();\n" +
                    "}\n" +
                    "\n" +
                    "class MyAscnyTask extends AsyncTask<Void, Integer, String>{\n" +
                    "    @Override\n" +
                    "    protected String doInBackground(Void... params) {\n" +
                    "        try {\n" +
                    "            Thread.sleep(5000);\n" +
                    "        } catch (InterruptedException e) {\n" +
                    "            e.printStackTrace();\n" +
                    "        }\n" +
                    "        return \"\";\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "}\n" +
                    "\n" +
                    "//解决方案：非静态内部类改为静态内部类\n" +
                    "public class MainActivity extends AppCompatActivity {\n" +
                    "\n" +
                    "```java\n" +
                    "@Override\n" +
                    "protected void onCreate(Bundle savedInstanceState) {\n" +
                    "    super.onCreate(savedInstanceState);\n" +
                    "    setContentView(R.layout.activity_main);\n" +
                    "    new MyAscnyTask().execute();\n" +
                    "}\n" +
                    "\n" +
                    "static class MyAscnyTask extends AsyncTask<Void, Integer, String>{\n" +
                    "    @Override\n" +
                    "    protected String doInBackground(Void... params) {\n" +
                    "        try {\n" +
                    "            Thread.sleep(50000);\n" +
                    "        } catch (InterruptedException e) {\n" +
                    "            e.printStackTrace();\n" +
                    "        }\n" +
                    "        return \"\";\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "}\n" +
                    "\n" +
                    "非静态内部类自动获得外部类的强引用，而且它的生命周期甚至比外部类更长，这便埋下了内存泄露的隐患。如果一个 Activity 的非静态内部类的生命周期比 Activity 更长，那么 Activity 的内存便无法被回收，也就是发生了内存泄露，而且还有可能发生难以预防的空指针问题。\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "#### 2.4. 为什么大神喜欢用static final来修饰常数？\n" +
                    "\n" +
                    "static由于是所有实例共享的，说到共享一定要加锁，万一某个实例更改它后，其它的实例也会受到影响，所以加入`final`作为永久只读锁以防止常数被修改。\n" +
                    "\n" +
                    "> 全局变量生命周期是classloader，有坑。你的activity在finish后变量并不会改变。\n" +
                    "> 这个在面试中经常遇到，问你经过多次计算后，static的值是多少。比如在Android中有个坑，最常见的就是把一个sharedpreference赋值给一个static变量，然后又把sharedpreference改变后，再次调用这个static变量，就发现变量并没有改变，这个在debug中很难发现。\n" +
                    "\n" +
                    "#### 2.5. 顺便说下final吧\n" +
                    "\n" +
                    "- final 变量：是只读的；\n" +
                    "- final 方法：是不能继承或者重写的。\n" +
                    "- final 引用：引用不能修改，但是对象本身的属性可以修改；\n" +
                    "- final class：不可继承；\n" +
                    "\n" +
                    "```\n" +
                    "final MyObject o = new MyObject();\n" +
                    "o.setValue(\"foo\"); // Works just fine\n" +
                    "o = new MyObject(); // Doesn't work.\n" +
                    "```\n" +
                    "\n" +
                    "- 虚拟机并不会知道你的变量是否是final的，所以final与内存泄露无关。\n" +
                    "- final不会让代码速度更快\n" +
                    "\n" +
                    "### 3. Bitmap的使用\n" +
                    "\n" +
                    "- 使用前注意配置Bitmap的Config，比如长宽，参数(565, 8888)，格式；\n" +
                    "- 使用中注意缓存；\n" +
                    "- 使用后注意recycle以清理native层的内存。\n" +
                    "\n" +
                    "> 2.3以后的bitmap不需要手动recycle了，内存已经在java层了。同时，Bitmap还有别人做好的轮子，比如PhotoView,Picasso，就可以方便的解决OOM问题。\n" +
                    "\n" +
                    "### 4. 多线程\n" +
                    "\n" +
                    "线程泄露可能是最严重的泄露问题了，第一它可能与Handler一样，转一转手机内存就没了，第二是当回调的时候，它极可能弹出`NullPointException`\n" +
                    "\n" +
                    "##### 个人在实际使用的一个失败实例\n" +
                    "\n" +
                    "上传图片时退出Activity，等到图片完成后，Toast就会抛出空指针异常。\n" +
                    "\n" +
                    "```\n" +
                    "//retrofit 1.9 bad sample \n" +
                    "RestAdapter adapter = new RestAdapter.Builder().setEndpoint(HeadlineService.END_POINT)\n" +
                    "            .setLogLevel(RestAdapter.LogLevel.FULL)\n" +
                    "            .build();\n" +
                    "adapter.create(ImageService.class)\n" +
                    "    .updateImage(new TypedFile(\"image/*\", file), new TypedString(nickname),\n" +
                    "        new TypedString(Build.MODEL), new TypedString(avatar),\n" +
                    "        new Callback<UploadResult>() {\n" +
                    "          @Override public void success(UploadResult uploadResult, Response response) {\n" +
                    "            if (uploadResult.getStatus() == 1) {\n" +
                    "              Log.d(TAG, \"upload successfully!\");\n" +
                    "              Toast.makeText(getActivity(), \"上传成功！\", Toast.LENGTH_SHORT)\n" +
                    "                  .show();\n" +
                    "            } else {\n" +
                    "              Log.e(TAG, \"upload failed!\");\n" +
                    "              Toast.makeText(getActivity(), \"上传失败！\", Toast.LENGTH_SHORT)\n" +
                    "                  .show();\n" +
                    "            }\n" +
                    "            bmp.recycle();\n" +
                    "          }\n" +
                    "\n" +
                    "          @Override public void failure(RetrofitError error) {\n" +
                    "            bmp.recycle();\n" +
                    "          }\n" +
                    "        });\n" +
                    "```\n" +
                    "\n" +
                    "我是使用Retrofit框架进行上传的，retrofit内部自己维护它的线程与生命周期，当我退出Activity时，Retrofit内部的网络线程并没有停止；当图片上传成功回调的时候，却发现window已经没了，这样就会抛出异常。\n" +
                    "\n" +
                    "解决方法：在Activity中使用耗时任务本来就不合适，使用Service可以更好的控制回调问题。\n" +
                    "\n" +
                    "### 5. Context与ApplicationContext\n" +
                    "\n" +
                    "| class    | Context                         | ApplicationContext                                           |\n" +
                    "| -------- | ------------------------------- | ------------------------------------------------------------ |\n" +
                    "| 生命周期 | 短                              | 非常长，几乎就是单例                                         |\n" +
                    "| 适用场景 | Activity中需要UI/素材资源的地方 | 数据库，包管理，偏好设置，以及Picasso/Retrofit/ShareSDK/Webview等单例框架 |\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "Context的生命周期是一个Activiy，而ApplicationContext的生命周期是整个程序。我们最要注意的就是Context的内存泄露。\n" +
                    "\n" +
                    "在Activiy的UI中要使用Context,而在其他的地方比如数据库、网络、系统服务的需要频繁调用Context的情况时，要使用ApplicationContext，以防止内存泄露。\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "```java\n" +
                    "//传入的Context是Activity时，单例持有Activity的强引用，这样即使该Activity退出，Activity内存也不会被回收，这样就造成了内存泄漏。\n" +
                    "//解决方案：使传入单例模式引用的对象的生命周期 = 应用生命周期\n" +
                    "//将 context.getApplicationContext() 赋值给 mContext，此时单例引用的对象是 Application，而 Application 的生命周期本来就跟应用程序是一样的，也就不存在内存泄露。\n" +
                    "public class SingleInstanceTest {\n" +
                    "\n" +
                    "    private static SingleInstanceTest sInstance;\n" +
                    "    private Context mContext;\n" +
                    "\n" +
                    "    private SingleInstanceTest(Context context){\n" +
                    "        this.mContext = context.getApplicationContext();\n" +
                    "    }\n" +
                    "\n" +
                    "    public static SingleInstanceTest newInstance(Context context){\n" +
                    "        if(sInstance == null){\n" +
                    "            sInstance = new SingleInstanceTest(context);\n" +
                    "        }\n" +
                    "        return sInstance;\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "```java\n" +
                    "//使用弱引用（WeakReference）进行保存Context实例\n" +
                    "public class Sample {\n" +
                    "\n" +
                    "    private WeakReference<Context> mWeakReference;\n" +
                    "\n" +
                    "    public Sample(Context context){\n" +
                    "        this.mWeakReference = new WeakReference<>(context);\n" +
                    "    }\n" +
                    "\n" +
                    "    public Context getContext() {\n" +
                    "        if(mWeakReference.get() != null){\n" +
                    "            return mWeakReference.get();\n" +
                    "        }\n" +
                    "        return null;\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "// 外部调用\n" +
                    "Sample sample = new Sample(MainActivity.this);\n" +
                    "```\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "### 6. 其他\n" +
                    "\n" +
                    "Handler 非静态内部Handler，应该把Handler改为静态内部类，持有Activity软引用进行使用，关闭Activity后要移除所有的消息\n" +
                    "\n" +
                    "Thread Activity结束线程还在运行\n" +
                    "\n" +
                    "TimerTask\n" +
                    "\n" +
                    "AsyncTask 非静态内部AsyncTask持有Activity引用，应当改为静态内部类加持有Activity软引用\n" +
                    "\n" +
                    "广播或其他资源注册后没有反注册\n" +
                    "\n" +
                    "创建对象后没有关闭\n" +
                    "\n" +
                    "集合\n" +
                    "\n" +
                    "Adapter没有使用缓存，主要指ListView\n" +
                    "\n" +
                    "IO，File， Cursor, ContentObserver, BroadCastRecelver\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "# 常见内存问题\n" +
                    "\n" +
                    "## 内存泄漏\n" +
                    "\n" +
                    "* 单例\n" +
                    "* 静态变量\n" +
                    "* Handler\n" +
                    "* 匿名内部类\n" +
                    "* 资源使用完未关闭\n" +
                    "\n" +
                    "## 图片分辨率相关\n" +
                    "\n" +
                    "## 分辨率适配\n" +
                    "\n" +
                    "很多情况下图片所占的内存在整个App内存占用中会占大部分。我们知道可以通过将图片放到hdpi/xhdpi/xxhdpi等不同文件夹进行适配，通过xml android:background设置背景图片，或者通过BitmapFactory.decodeResource()方法，图片实际上默认情况下是会进行缩放的。在Java层实际调用的函数都是或者通过BitmapFactory里的decodeResourceStream函数\n" +
                    "\n" +
                    "```\n" +
                    "public static Bitmap decodeResourceStream(Resources res, TypedValue value,\n" +
                    "        InputStream is, Rect pad, Options opts) \n" +
                    "{\n" +
                    "    if (opts == null)\n" +
                    "    {\n" +
                    "        opts = new Options();\n" +
                    "    }\n" +
                    "    if (opts.inDensity == 0 && value != null)\n" +
                    "    {\n" +
                    "       final int density = value.density;\n" +
                    "       if (density == TypedValue.DENSITY_DEFAULT)\n" +
                    "       {\n" +
                    "           opts.inDensity = DisplayMetrics.DENSITY_DEFAULT;\n" +
                    "       }\n" +
                    "       else if (density != TypedValue.DENSITY_NONE)\n" +
                    "       {\n" +
                    "           opts.inDensity = density;\n" +
                    "       }\n" +
                    "    }    \n" +
                    "    if (opts.inTargetDensity == 0 && res != null)\n" +
                    "    {\n" +
                    "        opts.inTargetDensity = res.getDisplayMetrics().densityDpi;\n" +
                    "    }    \n" +
                    "    return decodeStream(is, pad, opts);\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "decodeResource在解析时会对Bitmap根据当前设备屏幕像素密度densityDpi的值进行缩放适配操作，使得解析出来的Bitmap与当前设备的分辨率匹配，达到一个最佳的显示效果，并且Bitmap的大小将比原始的大，可以参考下腾讯Bugly的详细分析Android 开发绕不过的坑：[你的 Bitmap 究竟占多大内存？](http://mp.weixin.qq.com/s?__biz=MzA3NTYzODYzMg==&mid=403263974&idx=1&sn=b0315addbc47f3c38e65d9c633a12cd6&scene=21#wechat_redirect)\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "## 图片压缩\n" +
                    "\n" +
                    "BitmapFactory 在解码图片时，可以带一个Options，有一些比较有用的功能，比如：\n" +
                    "\n" +
                    "- **inTargetDensity** 表示要被画出来时的目标像素密度\n" +
                    "- **inSampleSize** 这个值是一个int，当它小于1的时候，将会被当做1处理，如果大于1，那么就会按照比例（1 / inSampleSize）缩小bitmap的宽和高、降低分辨率，大于1时这个值将会被处置为2的倍数。例如，width=100，height=100，inSampleSize=2，那么就会将bitmap处理为，width=50，height=50，宽高降为1 / 2，像素数降为1 / 4\n" +
                    "- **inJustDecodeBounds** 字面意思就可以理解就是只解析图片的边界，有时如果只是为了获取图片的大小就可以用这个，而不必直接加载整张图片。\n" +
                    "- **inPreferredConfig** 默认会使用ARGB_8888,在这个模式下一个像素点将会占用4个byte,而对一些没有透明度要求或者图片质量要求不高的图片，可以使用RGB_565，一个像素只会占用2个byte，一下可以省下50%内存。\n" +
                    "- **inPurgeable**和**inInputShareable** 这两个需要一起使用，BitmapFactory.java的源码里面有注释，大致意思是表示在系统内存不足时是否可以回收这个bitmap，有点类似软引用，但是实际在5.0以后这两个属性已经被忽略，因为系统认为回收后再解码实际会反而可能导致性能问题\n" +
                    "- **inBitmap** 官方推荐使用的参数，表示重复利用图片内存，减少内存分配，在4.4以前只有相同大小的图片内存区域可以复用，4.4以后只要原有的图片比将要解码的图片大既可以复用了。\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "## 缓存池\n" +
                    "\n" +
                    "现在很多图片加载组件都不仅仅是使用软引用或者弱引用了，实际上类似Glide 默认使用的事LruCache，因为软引用 弱引用都比较难以控制，使用LruCache可以实现比较精细的控制，而默认缓存池设置太大了会导致浪费内存，设置小了又会导致图片经常被回收，所以需要根据每个App的情况，以及设备的分辨率，内存计算出一个比较合理的初始值，可以参考Glide的做法。\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "## 内存抖动： 频繁分配和回收\n" +
                    "\n" +
                    "\n"
        )
    }

    override fun getPresenter(): IPresenter? {
        return null
    }

    override fun getContentViewRes(): Int {
        return R.layout.activity_subject_detail
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

}
