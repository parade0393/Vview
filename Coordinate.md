坐标系：
left = getLeft();top = getTop();right = getRight();bottom = getBottom();
这四个方法获取的坐标表示的是View原始状态时相对于父容器的坐标，对View进行平移操作并不会改变着四个方法的返回值

x=getX();translationX=getTranslationX()；
getX()与getY()方法获取的是View左上角相对于父容器的坐标，当View没有发生平移操作时，getX()==getLeft()、getY()==getTop()
x表示view相对于父布局左上角的坐标，会随着view的移动改变。
translationX表示view左上角相对于当前view原始位置x方向移动的距离，在view不移动时默认为0
x = left + translationX