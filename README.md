# Welcome to the AndroidBreadCumb!


![](http://s1.upload7.ir/downloads/KEtHSjKtXSQTq5dgaepNe5AQzAenYpcA/Screenshot_2015-09-01-19-26-44.jpeg)

breadcumb of above created by this code in one of my project

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
        breadCumb.UpdatePath();

`
