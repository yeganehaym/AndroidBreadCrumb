# Welcome to the AndroidBreadCumb!


![](http://s1.upload7.ir/downloads/KEtHSjKtXSQTq5dgaepNe5AQzAenYpcA/Screenshot_2015-09-01-19-26-44.jpeg)

breadcumb of above created by a code like this in one of my project

i's smart to control itself in any screenSize and control items when screen has no space anymore to fit items

`

        AndBreadCumb breadCumb=new AndBreadCumb(context);
        breadCumb.setLayout(...);
        breadCumb.setRTL(true);
        breadCumb.AddNewItem(new AndBreadCumbItem(0, "Home"));
        breadCumb.AddNewItem(new AndBreadCumbItem(12, "Category1"));
        breadCumb.SetTinyNextNodeImage(R.drawable.arrow);
        breadCumb.SetViewStyleId(R.drawable.list_item_style);
        breadCumb.setTextSize(25);

        breadCumb.setOnTextViewUpdate(new ITextViewUpdate() {
            @Override
            public TextView UpdateTextView(Context context, TextView tv) {
                //do anything you want on each text
            }
        });
        //when user needs an event to control clicks on items
          breadCumb.setOnClickListener(new IClickListener() {
            @Override
            public void onClick(int position, int Id) {

                breadCumb.SetPosition(position);
                //id is the value  you sat by AndBreadCumbItem like 0 or 12
            }
        });
        breadCumb.UpdatePath();

`

when elements are more than 4 and there is no space for them

(...) is a fake path and will open real path when user clicks on it

![](http://s1.upload7.ir/downloads/uponPbGDhdcqXSBOT7YBEjr2H5QfsnFN/Screenshot_2015-09-01-20-51-10%20-%20Copy.jpeg)

 if you dont want breadcumb class mange to space set 'setNoResize' to true
