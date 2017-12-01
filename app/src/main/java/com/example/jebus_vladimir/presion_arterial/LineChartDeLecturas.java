package com.example.jebus_vladimir.presion_arterial;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.jebus_vladimir.notimportant.MiConjuntoSeekBar;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LineChartDeLecturas extends Activity implements OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    private LineChart mChart;
    private MiConjuntoSeekBar mSeekBarTime, mSeekBarDays;
    protected Typeface mTfRegular;
    protected Typeface mTfLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_linechart_de_lecturas);

        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");

        mSeekBarTime = new MiConjuntoSeekBar( this, (SeekBar) findViewById(R.id.seekBar1),
                (TextView) findViewById(R.id.timeValue),0, 0 );
        mSeekBarDays = new MiConjuntoSeekBar( this, (SeekBar) findViewById(R.id.seekBar2),
                (TextView) findViewById(R.id.dayValue),7, 7 );

        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.LTGRAY);

        // add data
        setData(7, 100);

        mChart.animateX(2500);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(LegendForm.LINE);
        l.setTypeface(mTfLight);
        l.setTextSize(11f);
        l.setTextColor(Color.BLACK);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        LimitLine uld = new LimitLine(120f, "Dia Upper Limit");
        uld.setLineWidth(4f);
        uld.enableDashedLine(10f, 10f, 0f);
        uld.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        uld.setTextSize(10f);
        uld.setTypeface(tf);
        uld.setLineColor(ColorTemplate.getHoloBlue());

        LimitLine lld = new LimitLine(50f, "Dia Lower Limit");
        lld.setLineWidth(4f);
        lld.enableDashedLine(10f, 10f, 0f);
        lld.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        lld.setTextSize(10f);
        lld.setTypeface(tf);
        lld.setLineColor(ColorTemplate.getHoloBlue());

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        //rightAxis.setTypeface(mTfLight);
        rightAxis.setTextColor(Color.BLUE);
        rightAxis.addLimitLine(uld);
        rightAxis.addLimitLine(lld);
        rightAxis.setAxisMaximum(200f);
        rightAxis.setAxisMinimum(20f);
        rightAxis.enableGridDashedLine(10f, 10f, 0f);
        rightAxis.setDrawZeroLine(false);
        //rightAxis.setDrawGridLines(false);
        //rightAxis.setGranularityEnabled(false);

        // limit lines are drawn behind data (and not on top)
        rightAxis.setDrawLimitLinesBehindData(true);

        LimitLine uls = new LimitLine(180f, "Sys Upper Limit");
        uls.setLineWidth(4f);
        uls.enableDashedLine(10f, 10f, 0f);
        uls.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
        uls.setTextSize(10f);
        uls.setTypeface(tf);

        LimitLine lls = new LimitLine(70f, "Sys Lower Limit");
        lls.setLineWidth(4f);
        lls.enableDashedLine(10f, 10f, 0f);
        lls.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);
        lls.setTextSize(10f);
        lls.setTypeface(tf);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(uls);
        leftAxis.addLimitLine(lls);
        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(20f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        //mChart.getAxisRight().setEnabled(false);

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if( fromUser )  {
            if( mSeekBarDays.seekB == seekBar )  {
                setData( mSeekBarDays.setProgress( progress, null ), mSeekBarTime.value + 1 );
            }  else  {
                setData( mSeekBarDays.value, mSeekBarTime.setProgress( progress, null ) + 1 );
            }
            // redraw
            mChart.invalidate();
        }
    }

    private void setData(int count, float range) {
        /*Calendar c1 = new GregorianCalendar(), c2 = new GregorianCalendar();
        c1.set(2017, 1, 1, 0, 0, 0 );
        c2.set(2017, 1, 2, 0, 0, 0 );
        String  v[], d[], t[];
        long piv, tmp, unDia = c2.getTimeInMillis() - c1.getTimeInMillis();*/
        float sys, dia;
        int i;
        String a, b;
        ArrayList<Entry> yVals1 = new ArrayList<Entry>(), yVals2 = new ArrayList<Entry>();
        DataBase sistema = new DataBase(this, "app", null, 1);
        SQLiteDatabase db = sistema.getWritableDatabase();
        String qu = "select * from lectura;";
        Cursor fila = db.rawQuery(qu, null);
        if (fila.moveToLast()) {
            i = 0;
            do  {
                //v = fila.getString( fila.getColumnIndex("fecha") ).split( " " );
                a = fila.getString( fila.getColumnIndex("alta") );
                b = fila.getString( fila.getColumnIndex("baja") );
                sys = dia = 0f;
                if( !a.equals(""))
                    sys = Float.parseFloat( a );
                if( !b.equals(""))
                    dia = Float.parseFloat( b );

                yVals1.add(new Entry(i, dia));
                yVals2.add(new Entry(i, sys));

                if( fila.isFirst() == true )
                    break;
                i++;
                fila.moveToPrevious();
            }  while( i < range );
        }
        db.close();

        LineDataSet set1, set2;
        LineData aux;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            aux = mChart.getData();
            ((LineDataSet) aux.getDataSetByIndex( 0 )).setValues(yVals1);
            ((LineDataSet) aux.getDataSetByIndex( 1 )).setValues(yVals2);

            aux.notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals1, "DIASTOLIC mm Hg / Day time");

            set1.setAxisDependency(AxisDependency.LEFT);
            set1.setValueTextColor(Color.BLACK);
            set1.setColor(Color.BLUE);
            set1.setCircleColor(Color.BLUE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);

            // create a dataset and give it a type
            set2 = new LineDataSet(yVals2, "SYSTOLIC mm Hg / Day time");
            set2.setAxisDependency(AxisDependency.RIGHT);
            set2.setColor(Color.RED);
            set2.setCircleColor(Color.RED);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));

            // create a data object with the datasets
            LineData data = new LineData(set1, set2);
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);

            // set data
            mChart.setData(data);
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());

        mChart.centerViewToAnimated(e.getX(), e.getY(), mChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency(), 500);
        //mChart.zoomAndCenterAnimated(2.5f, 2.5f, e.getX(), e.getY(), mChart.getData().getDataSetByIndex(dataSetIndex)
        // .getAxisDependency(), 1000);
        //mChart.zoomAndCenterAnimated(1.8f, 1.8f, e.getX(), e.getY(), mChart.getData().getDataSetByIndex(dataSetIndex)
        // .getAxisDependency(), 1000);
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }
    private long getMeD( String[] fecha ){
        Calendar cal = new GregorianCalendar();

        String v[] = fecha[0].split( "-" ), v2[];

        if( fecha[1] == null ) {
            cal.set(Integer.parseInt(v[0]), Integer.parseInt(v[1]), Integer.parseInt(v[2]));
        }  else  {
            v2 = fecha[1].split( ":" );
            cal.set(Integer.parseInt(v[0]), Integer.parseInt(v[1]), Integer.parseInt(v[2]),
                    Integer.parseInt(v2[0]), Integer.parseInt(v2[1]), Integer.parseInt(v2[2]));
        }

        return cal.getTimeInMillis();
    }
}
