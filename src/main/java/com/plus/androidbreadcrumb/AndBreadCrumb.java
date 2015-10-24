package com.plus.androidbreadcrumb;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ali on 8/8/2015.
 *
 * this class create an address bar or breadcumb on a linear layout for user can reading path of a tree for example
 */
public class AndBreadCrumb {

    //=-=--=-=-=-=-=-=-=-=-=-=-=- Private Properties -=-=-=-=-=-=-=--=-=-=
    private List<AndBreadCrumbItem> items=null;
    private List<TextView> textViews;
    private int tinyNextNodeImage;
    private int viewStyleId;
    private Context context;
    private boolean RTL;
    private float textSize=20;
    private boolean noResize=false;

    LinearLayout layout;
    IClickListener clickListener;
    ITextViewUpdate textViewUpdate;
    LinearLayout.LayoutParams params ;

    //=-=---=-=-=-=-- Constructor =--=-=-=-=-=--=-=-

    public AndBreadCrumb(Context context)
    {
        this.context=context;
        params = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    //=-=-=--=--=-=-=-=-=-=-=-=-  Public Properties --=-=-=-=-=-=--=-=-=-=-=-=-

    //each category would be added to create path
    public void AddNewItem(AndBreadCrumbItem item)
    {
        if(items==null)
            items=new ArrayList<>();
        items.add(item);
    }

    // if you want a pointer or next node between categories or textviews
    public void SetTinyNextNodeImage(int resId) {this.tinyNextNodeImage=resId;}

    public void SetViewStyleId(int resId) {this.viewStyleId=resId;}

    public void setTextSize(float textSize) {this.textSize = textSize;}

    public boolean isRTL() {
        return RTL;
    }

    public void setRTL(boolean RTL) {
        this.RTL = RTL;
    }

    public void setLayout(LinearLayout layout) {

        this.layout = layout;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isNoResize() {
        return noResize;
    }

    public void setNoResize(boolean noResize) {
        this.noResize = noResize;
    }

    //=-=-=-=-=-=-=-==---==-=-=-=-=- Private Methods -=-=--=-=-=-=-=-=-=-=-=

    //prepare textviews with special settings
    private TextView MakeTextView(final int position, final int Id)
    {
        //settings for cumbs
        TextView tv=new TextView(this.context);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setSingleLine(true);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tv.setBackgroundResource(viewStyleId);

        /*call custom event - this event will be fired when user click on one of
         textviews and returns position of textview and value that user sat as id
         */
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SetPosition(position);
                clickListener.onClick(position, Id);
            }
        });

        //if user wants to update each textviews
        if(textViewUpdate!=null)
            tv=textViewUpdate.UpdateTextView(context,tv);

        if(isRTL())
            tv.setRotationY(180);

        return tv;
    }

    //primary method for render objects on layout
    private void DrawPath() {


        //stop here if essentail elements aren't present
        if (items == null) return ;
        if (layout == null) return;
        if (items.size() == 0) return;


//we need to get size of layout,so we use the post method to run this thread when ui is ready
        layout.post(new Runnable() {
            @Override
            public void run() {


                //textviews created here one by one
                int position = 0;
                textViews = new ArrayList<>();
                for (AndBreadCrumbItem item : items) {
                    TextView tv = MakeTextView(position, item.getId());
                    tv.setText(item.getDiplayText());
                    textViews.add(tv);
                    position++;
                }


                //add textviews on layout
                AddTextViewsOnLayout();

                //we dont manage resizing anymore
                if(isNoResize()) return;

                //run this code after textviews Added to get widths of them
                TextView last_tv=textViews.get(textViews.size()-1);
                last_tv.post(new Runnable() {
                    @Override
                    public void run() {
                        //define width of each textview depend on screen width
                        BatchSizeOperation();
                    }
                });

            }
        });


    }

    //this method calling by everywhere to needs add textviews on the layout like master method :drawpath
    private void AddTextViewsOnLayout()
    {
        //prepare layout
        //remove everything on layout for recreate it
        layout.removeAllViews();
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setVerticalGravity(Gravity.CENTER_VERTICAL);
        if(isRTL())
            layout.setRotationY(180);



        //add textviews one by one

        int position=0;
        for (TextView tv:textViews)
        {
            layout.addView(tv,params);

            //add next node image between textviews if user defined a next node image
            if(tinyNextNodeImage>0)
                if(position<(textViews.size()-1)) {
                    layout.addView(GetNodeImage(), params);
                    position++;
                }
        }

    }

    //make next node image and set default values for that
    private ImageView GetNodeImage()
    {
        ImageView img=new ImageView(context);
        img.setBackgroundResource(tinyNextNodeImage);

        if(isRTL())
            img.setRotationY(180);

        return img;
    }


    //set textview width depend on screen width
    private void  BatchSizeOperation()
    {
        //get width of next node between cumbs
        Bitmap tinyBmap = BitmapFactory.decodeResource(context.getResources(), tinyNextNodeImage);
        int tinysize=tinyBmap.getWidth();
        //get sum of nodes
        tinysize*=(textViews.size()-1);


        //get width size of screen(layout is screen here)
        int screenWidth=GetLayoutWidthSize();

        //get sum of arrows and cumbs width
        int sumtvs=tinysize;
        for (TextView tv : textViews) {

            int width=tv.getWidth();
            sumtvs += width;
        }

    //if sum of cumbs is less than screen size the state is good so return same old textviews
        if(sumtvs<screenWidth)
            return ;


        if(textViews.size()>3)
        {
            //make fake path
            MakeFakePath();

            //clear layout and add textviews again
            AddTextViewsOnLayout();
        }

        //get free space without next nodes -> and spilt rest of space to textviews count to get space for each textview
        int freespace =screenWidth-tinysize;
        int each_width=freespace/textViews.size();

        //some elements have less than each_width,so we should leave size them and calculate more space again
        int view_count=0;
        for (TextView tv:textViews)
        {
            if (tv.getWidth()<=each_width)
                freespace=freespace-tv.getWidth();
            else
                view_count++;
        }
        if (view_count==0) return;

        each_width=freespace/view_count;
        for (TextView tv:textViews)
        {
            if (tv.getWidth()>each_width)
                tv.setWidth(each_width);
        }


    }

    //if elements are so much(mor than 3),we make a fake path to decrease elements
    private void MakeFakePath()
    {
        //we make 4 new elements that index 1 is fake element and has a rest of real path in its heart
        //when user click on it,path would be opened
        textViews=new ArrayList<>(4);
        TextView[] tvs=new TextView[4];
        int[] positions= {0,items.size()-3,items.size()-2,items.size()-1};

        for (int i=0;i<4;i++)
        {
            //request for new textviews
            tvs[i]=MakeTextView(positions[i],items.get(positions[i]).getId());

            if(i!=1)
                tvs[i].setText(items.get(positions[i]).getDiplayText());
            else {
                tvs[i].setText("...");
                //override click event and change it to part of code to open real path by call setposition method and redraw path
                tvs[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = items.size() - 3;
                        int id = items.get(pos).getId();
                        SetPosition(items.size() - 3);
                        clickListener.onClick(pos, id);
                    }
                });
            }
            textViews.add(tvs[i]);
        }
    }


    private int GetLayoutWidthSize()
    {
        int width=layout.getWidth();
        int padding=layout.getPaddingLeft()+layout.getPaddingRight();
        width-=padding;
        return width;
    }
    //=-=-=-=-=-=-=-=-=- Public Methods -==-=-=-=-==--=-=-=-=-=

    public void UpdatePath()
    {
        DrawPath();
    }

    public int CurrentPosition(){return items.size()-1;}

    public void SetPosition(int position)
    {
        if(position<0 || position>=textViews.size()) return ;

        int i=items.size()-1;
        for (;i>position;i--)
        {
            items.remove(i);
        }

        DrawPath();
    }

    //==--=-=-=-=--=-=-==-= Events =-=-=--=-=-=-==-=-=-=-=-=-=-=-=

    public void setOnClickListener (IClickListener listener)
    {
        // Store the listener object
        this.clickListener=listener;
    }

    public void setOnTextViewUpdate (ITextViewUpdate listener)
    {
        // Store the listener object
        this.textViewUpdate=listener;
    }
}
