[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ShimmerLayout-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/5767)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.supercharge/shimmerlayout/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/io.supercharge/shimmerlayout)
[![Android Weekly](http://img.shields.io/badge/Android%20Weekly-%23266-2CB3E5.svg?style=flat)](http://androidweekly.net/issues/issue-266)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![Build Status](https://travis-ci.org/team-supercharge/ShimmerLayout.svg?branch=master)](https://travis-ci.org/team-supercharge/ShimmerLayout)

# ShimmerLayout

🌎  Traduções: [🇧🇷](https://github.com/brunomunizaf/ShimmerLayout/blob/master/README_pt-br.md) by [@brunomunizaf](https://twitter.com/brunomuniz_af)

`ShimmerLayout` pode ser usado para adicionar o efeito de brilho (como o usado no Facebook ou no LinkedIn) no seu App Android. Além de ser eficiênte em memória até mesmo animando um grande layout, você pode customizar o comportamento e o visual da animação. Verifique a [**wiki**](https://github.com/team-supercharge/ShimmerLayout/wiki/Home) para atributos!

<p align="center">
<img src="/shimmerlayout.gif?raw=true" width="300" />
</p>

# Performance

Quando falamos de performance, `ShimmerLayout` é a melhor opção disponível. Você tem não só uma **alta taxa de frames** até em RecyclerViews, mas também **animaçōes com custo de memória insignificate.**

Eu conduzi um benchmark para comparar uso de memória entre diferentes implementações de brilho (o efeito shimmer). O dispositivo de teste que eu usei for um celular HTC M8. Para as animações, o app de exemplo foi usado.

| Library usada | Resultado do Android Profiler |
| --- | --- |
| Facebook shimmer library 0.1.0        | ![ShimmerLayout](benchmark_images/facebook_0_1_0.PNG)             |
| ShimmerLayout 1.2.0                   | ![ShimmerLayout](benchmark_images/shimmer_layout_1_2_0.PNG)       |
| **ShimmerLayout 2.0.0**               | ![ShimmerLayout](benchmark_images/shimmer_layout_2_0_0.PNG)       |

# Download e uso

Obtenha o último artifact via gradle
```groovy
implementation 'io.supercharge:shimmerlayout:2.1.0'
```

Crie o layout no qual você deseja aplicar o efeito e adicione como child de uma `ShimmerLayout`

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
android:gravity="center"
android:text="ShimmerLayout"
android:textColor="@color/shimmer_background_color"
android:textSize="26sp"/>
</io.supercharge.shimmerlayout.ShimmerLayout>
```

Por último, mas não menos importante você precisa iniciá-lo no seu código Java
```java
ShimmerLayout shimmerText = (ShimmerLayout) findViewById(R.id.shimmer_text);
shimmerText.startShimmerAnimation();
```
# Mais sobre

* [The development of ShimmerLayout](https://medium.com/supercharges-mobile-product-guide/shimmerlayout-26978ab53c28)  - Nesse artigo você pode ler porquê nós criamos essa library e as tecnologias que usamos.

# Licença

`ShimmerLayout` é código aberto, contribuições e feedbacks são bem-vindos!

[Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)


```
Copyright 2017 Supercharge

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
# Autor

[veghtomi](https://github.com/veghtomi)   

[![Supercharge](http://s23.postimg.org/gbpv7dwjr/unnamed.png)](http://supercharge.io/)
