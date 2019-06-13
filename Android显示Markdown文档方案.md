# Android显示Markdown文档方案

## 使用WebView加载

问题： 

* 不能适应屏幕宽度，左右也能拖动
* 公式问题



## 使用原生TextView或其他控件加载

* FlexibleRichTextView 
  * 链接问题
  * 图片解析
* Markwon
  * 图片相对链接问题
  * Gif问题
  * 加上公式支持库，加载不出来，性能也有问题
    1. 公式头的$ $之前不能有空格；公式尾的$ $之后不能有空格。会造成解析错误
    2. 多行公式的处理
    3. $ $的识别和处理
    4. $$ $同时存在时的配对和处理

LaTeX库公式空格：
quad空格，符号：\quad，如：$x \quad y$
大空格，符号\，如：$x \ y$

## LaTeX转换公式为图片发现的问题
    Markwon库以源码形式引入了LaTeX库，其中JavaFontRenderingAtom等类被魔改，传入的某些字符串处理过程中会报空指针等异常。
    解决方案：LaTeX传入公式字符串处理之前，先把全角字符转换为半角字符，代码如下：
```java
    public static String ToDBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);

            }
        }
        return new String(c);
    }
```



## 采用过的库

1. implementation 'us.feras.mdv:markdownview:1.1.0'

   WebView加载Markdown。问题：无法处理相对链接图片，公式，表格

2. 

