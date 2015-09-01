# Welcome to the AndroidBreadCrumb!


![](http://s1.upload7.ir/downloads/KEtHSjKtXSQTq5dgaepNe5AQzAenYpcA/Screenshot_2015-09-01-19-26-44.jpeg)

breadcumb of above created by a code like this in one of my project

it's smart to control itself in any screenSize and control items when screen has no space anymore to fit items

`

        AndBreadCrumb breadCrumb=new AndBreadCrumb(context);
        breadCrumb.setLayout(...);
        breadCrumb.setRTL(true);
        breadCrumb.AddNewItem(new AndBreadCumbItem(0, "Home"));
        breadCrumb.AddNewItem(new AndBreadCumbItem(12, "Category1"));
        breadCrumb.SetTinyNextNodeImage(R.drawable.arrow);
        breadCrumb.SetViewStyleId(R.drawable.list_item_style);
        breadCrumb.setTextSize(25);

        breadCrumb.setOnTextViewUpdate(new ITextViewUpdate() {
            @Override
            public TextView UpdateTextView(Context context, TextView tv) {
                //do anything you want on each text
            }
        });
        //when user needs an event to control clicks on items
          breadCrumb.setOnClickListener(new IClickListener() {
            @Override
            public void onClick(int position, int Id) {

                breadCrumb.SetPosition(position);
                //id is the value  you sat by AndBreadCumbItem like 0 or 12
            }
        });
        breadCrumb.UpdatePath();

`

when elements are more than 4 and there is no space for them

(...) is a fake path and will open real path when user clicks on it

![](http://s1.upload7.ir/downloads/uponPbGDhdcqXSBOT7YBEjr2H5QfsnFN/Screenshot_2015-09-01-20-51-10%20-%20Copy.jpeg)

 if you dont want breadcumb class mange to space set 'setNoResize' to true
