package com.xspace.xspace;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AndroidSQLite extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_sqlite);

        DatabaseHandler db=new DatabaseHandler(this);

        List<Contact> contacts=db.getAllContacts();
        final ArrayList<String> list=new ArrayList<String>();

        for(Contact ct:contacts){
            list.add(ct.getName());
       }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,android.R.layout.simple_list_item_1,list);

        ListView lv = (ListView) findViewById(R.id.list_view_1);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id){
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        list.remove(item);
                        adapter.notifyDataSetChanged();
                        view.setAlpha(1);
                    }
                });
            }
        });
    }
    private class StableArrayAdapter extends ArrayAdapter<String>{
        HashMap<String, Integer> mIdMap=new HashMap<String, Integer>();
        public StableArrayAdapter(Context context, int textViewResourceId,List<String> objects){
            super(context,textViewResourceId,objects);
            for (int i=0;i<objects.size();i++){
                mIdMap.put(objects.get(i),i);
            }
        }

        @Override
        public long getItemId(int position){
            String item=getItem(position);
            return mIdMap.get(item);
        }
        @Override
        public boolean hasStableIds(){
            return true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_android_sqlite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onCreateData(View v){
        DatabaseHandler db=new DatabaseHandler(this);
        /**
         * create operations
         */
        Log.d("Insert: ","Inserting ..");
        db.addContact(new Contact("Xavi Alonso 2", "123456789"));
        db.addContact(new Contact("Fernando Torres v2", "012454544"));
        db.addContact(new Contact("Frank Lampard 2","4541332447"));
        Log.d("Reading: ","Reading all contacts ..");
        List<Contact> contacts=db.getAllContacts();
        ArrayList<String> name_list= new ArrayList<String>();
        for(Contact ct:contacts){
            name_list.add(ct.getName());
            String log="Id: "+ct.getID()+" ,Name: "+ct.getName()+" ,Phone: "+ct.getPhoneNumber();
            Log.d("Name: ",log);
        }
        ListView lv = (ListView) findViewById(R.id.list_view_1);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,name_list);
        lv.setAdapter(arrayAdapter);
    }
}
