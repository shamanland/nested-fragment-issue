onActivityResult not being called in Fragment
====

Read more details in [original post][1].

Description
----

``FragmentActivity`` replace ``requestCode`` by modified one. After that, when ``onActivityResult()`` will be invoked, ``FragmentActivity`` parse higher 16 bits and restore index of original Fragment. Look at this scheme:

![enter image description here][2]

If you have few fragments at the root level there is no problems. But if you have **nested fragments**, for example ``Fragment`` with few tabs inside ``ViewPager``, you *guaranteed will face with a problem* (or already faced it).

Because **only one index** is stored inside ``requestCode``. That is index of ``Fragment`` inside it's ``FragmentManager``. When we using nested fragments, there are child ``FragmentManager``, which has own list of Fragments. So, it's necessary to save whole chain of indices, starting from root ``FragmentManager``.

![enter image description here][3]

Solution
----

Compile this project and look at the [CommonActivity.java][4].

  [1]: http://blog.shamanland.com/2014/01/nested-fragments-for-result.html
  [2]: http://i.stack.imgur.com/F92yr.png
  [3]: http://i.stack.imgur.com/qgcFV.png
  [4]: https://github.com/shamanland/nested-fragment-issue/blob/master/app/src/main/java/com/shamanland/common/app/CommonActivity.java
