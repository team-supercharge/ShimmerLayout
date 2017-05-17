# ShimmerLayout

ShimmerLayout can be used to add shimmer effect (like the one used at Facebook or at LinkedIn) to your android application. Beside memory efficiency even animating a big layout, you can modify the shimmer color and the speed of the animation as well.

![ShimmerLayout](shimmerlayout.gif)

# Download and usage

Get the lasest artifact via gradle
```
compile 'io.supercharge:shimmerlayout:0.2.0'
```

Create the layout on which you want to apply the effect and add as a child of a ShimmerLayout

```xml
<io.supercharge.shimmerlayout.ShimmerLayout
        android:id="@+id/shimmer_text"
        android:layout_width="wrap_content"
        android:layout_height="40dp"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="50dp"
        android:paddingLeft="30dp"
        android:paddingRight="30dp"
        app:shimmer_animation_duration="1200">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:layout_centerInParent="true"
            android:gravity="center"
            android:text="ShimmerLayout"
            android:textColor="@color/shimmer_background_color"
            android:textSize="26sp"/>
    </io.supercharge.shimmerlayout.ShimmerLayout>
```

Last, but not least you have start it from your Java code
```java
ShimmerLayout shimmerText = (ShimmerLayout) findViewById(R.id.shimmer_text);
shimmerText.startShimmerAnimation();
```
# License

ShimmerLayout is opensource, contribution and feedback are welcome!

[Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)


```
Copyright 2015 Supercharge

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
# Author

[veghtomi](https://github.com/veghtomi)   

[![Supercharge](http://s23.postimg.org/gbpv7dwjr/unnamed.png)](http://supercharge.io/)