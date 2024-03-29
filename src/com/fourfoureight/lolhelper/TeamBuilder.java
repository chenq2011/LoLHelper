package com.fourfoureight.lolhelper;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.fourfoureight.lolhelper.General_Info.ChampionAttributes;

// Description:
// This is the main Team Builder class.
// It deals with the user interface for the team builder page.

public class TeamBuilder extends ActionBarActivity {
    
	// Create an instance for the data storage class.
    TeamBuilderData teambuilder = new TeamBuilderData();
	// button pressed = 1, button not pressed = 0
    int[] buttonPressFlags = {-1, -1, -1, -1, -1};
	// button is displaying a champion = 1, button is not displaying a champion = 0
    int[] buttonChangedFlags = {0, 0, 0, 0, 0};
        
	// Run when the team builder page created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_builder);
        
        FrameLayout layout = (FrameLayout)findViewById(R.id.container);
        if (((GlobalVariables) this.getApplication()).getskin() == 1)
    	{
    		layout.setBackgroundResource(R.drawable.bg);
    	}
    	if (((GlobalVariables) this.getApplication()).getskin() == 2)
    	{
    		layout.setBackgroundResource(R.drawable.bg2);
    	}
        
    	// five buttons on the left
        final ImageButton top = (ImageButton)findViewById(R.id.imageButton00);
        final ImageButton jungle = (ImageButton)findViewById(R.id.imageButton01);
        final ImageButton middle = (ImageButton)findViewById(R.id.imageButton02);
        final ImageButton adc = (ImageButton)findViewById(R.id.imageButton03);
        final ImageButton support = (ImageButton)findViewById(R.id.imageButton04);
        // button "Suggest Champion" is invisible
        final Button suggestChampion = (Button)findViewById(R.id.Button06);
        // button "Suggest Style"
        final Button suggestStyle = (Button)findViewById(R.id.Button05);
        // spinner to selecting team styles
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        // a table layout to display "All Available Champions"	
        final TableLayout table = (TableLayout)findViewById(R.id.Table);
        
        // set the display content for spinner
        String[] allStrategies = teambuilder.getAvailableStrategy();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allStrategies);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        // selector control for spinner
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                	teambuilder.setOurStrategy(-1);
                }
                else {
                	teambuilder.setOurStrategy(teambuilder.getAvailableStrategyArray(position));
                }
            	
			suggestChampion.performClick();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                teambuilder.setOurStrategy(-1);
            }
        });
                        
        // button Suggest Style
        suggestStyle.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				teambuilder.suggestStrategyForTeam();
				// update the contents in spinner
                String[] allStrategies = teambuilder.getAvailableStrategy();
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(TeamBuilder.this, android.R.layout.simple_spinner_item, allStrategies);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(adapter1);
				suggestChampion.performClick();
			}
		});
        
        // button Suggest Champion
        suggestChampion.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				
				// horizontal scroll bars after five buttons to display suggested champions.
				LinearLayout scroll1 = (LinearLayout)findViewById(R.id.Linear1);
				LinearLayout scroll2 = (LinearLayout)findViewById(R.id.Linear2);
				LinearLayout scroll3 = (LinearLayout)findViewById(R.id.Linear3);
				LinearLayout scroll4 = (LinearLayout)findViewById(R.id.Linear4);
				LinearLayout scroll5 = (LinearLayout)findViewById(R.id.Linear5);
				
				scroll1.removeAllViews();
				scroll2.removeAllViews();
				scroll3.removeAllViews();
				scroll4.removeAllViews();
				scroll5.removeAllViews();
				
				// suggested top champions
				ChampionAttributes[] suggestedTop = teambuilder.suggestChampionsByStrategy(0);
				if ((suggestedTop != null) && (buttonChangedFlags[0] == 0)){
					for (int i = 0; i < suggestedTop.length; i++){
						final ImageButton newButton = initializeButton(new ImageButton(TeamBuilder.this), suggestedTop[i]);
						if (suggestedTop[i].getName().equals("NONAME")){
							newButton.setVisibility(View.GONE);
						}
						scroll1.addView(newButton);
						final ChampionAttributes currentChampion = suggestedTop[i];
						newButton.setOnClickListener(new View.OnClickListener(){
							@Override
					        public void onClick(View v){
								if (buttonPressFlags[0] == 1){
									teambuilder.setOurTeam(0, currentChampion);
							    	String message = currentChampion.getName();
							    	top.setImageResource(getResources().getIdentifier(
							    			message.replaceAll("[^a-zA-Z]+","").toLowerCase(), "drawable", getPackageName()));

									buttonPressFlags[0] = -1;
									buttonPressFlags[1] = -1;
									buttonPressFlags[2] = -1;
									buttonPressFlags[3] = -1;
									buttonPressFlags[4] = -1;
									buttonChangedFlags[0] = 1;
									newButton.setVisibility(View.GONE);
									suggestChampion.performClick();
								}
					        }
						});
					}
				}
				
				// suggested jungle champions
				ChampionAttributes[] suggestedJungle = teambuilder.suggestChampionsByStrategy(1);
				if ((suggestedJungle != null) && (buttonChangedFlags[1] == 0)){
					for (int i = 0; i < suggestedJungle.length; i++){
						final ImageButton newButton = initializeButton(new ImageButton(TeamBuilder.this), suggestedJungle[i]);
						if (suggestedJungle[i].getName().equals("NONAME")){
							newButton.setVisibility(View.GONE);
						}
						scroll2.addView(newButton);
						final ChampionAttributes currentChampion = suggestedJungle[i];
						newButton.setOnClickListener(new View.OnClickListener(){
							@Override
					        public void onClick(View v){
								if (buttonPressFlags[1] == 1){
									teambuilder.setOurTeam(1, currentChampion);
							    	String message = currentChampion.getName();
							    	jungle.setImageResource(getResources().getIdentifier(
							    			message.replaceAll("[^a-zA-Z]+","").toLowerCase(), "drawable", getPackageName()));

									buttonPressFlags[0] = -1;
									buttonPressFlags[1] = -1;
									buttonPressFlags[2] = -1;
									buttonPressFlags[3] = -1;
									buttonPressFlags[4] = -1;
									buttonChangedFlags[1] = 1;
									newButton.setVisibility(View.GONE);
									suggestChampion.performClick();
								}
					        }
						});
					}
				}

				// Suggested middle champions				
				ChampionAttributes[] suggestedMid = teambuilder.suggestChampionsByStrategy(2);
				if ((suggestedMid != null) && (buttonChangedFlags[2] == 0)){
					for (int i = 0; i < suggestedMid.length; i++){
						final ImageButton newButton = initializeButton(new ImageButton(TeamBuilder.this), suggestedMid[i]);
						if (suggestedMid[i].getName().equals("NONAME")){
							newButton.setVisibility(View.GONE);
						}
						scroll3.addView(newButton);
						final ChampionAttributes currentChampion = suggestedMid[i];
						newButton.setOnClickListener(new View.OnClickListener(){
							@Override
					        public void onClick(View v){
								if (buttonPressFlags[2] == 1){
									teambuilder.setOurTeam(2, currentChampion);
							    	String message = currentChampion.getName();
							    	middle.setImageResource(getResources().getIdentifier(
							    			message.replaceAll("[^a-zA-Z]+","").toLowerCase(), "drawable", getPackageName()));

									buttonPressFlags[0] = -1;
									buttonPressFlags[1] = -1;
									buttonPressFlags[2] = -1;
									buttonPressFlags[3] = -1;
									buttonPressFlags[4] = -1;
									buttonChangedFlags[2] = 1;
									newButton.setVisibility(View.GONE);
									suggestChampion.performClick();
								}
					        }
						});
					}
				}
				
				// suggested ADCs
				ChampionAttributes[] suggestedADC = teambuilder.suggestChampionsByStrategy(3);
				if ((suggestedADC != null) && (buttonChangedFlags[3] == 0)){
					for (int i = 0; i < suggestedADC.length; i++){
						final ImageButton newButton = initializeButton(new ImageButton(TeamBuilder.this), suggestedADC[i]);
						if (suggestedADC[i].getName().equals("NONAME")){
							newButton.setVisibility(View.GONE);
						}
						scroll4.addView(newButton);
						final ChampionAttributes currentChampion = suggestedADC[i];
						newButton.setOnClickListener(new View.OnClickListener(){
							@Override
					        public void onClick(View v){
								if (buttonPressFlags[3] == 1){
									teambuilder.setOurTeam(3, currentChampion);
							    	String message = currentChampion.getName();
							    	adc.setImageResource(getResources().getIdentifier(
							    			message.replaceAll("[^a-zA-Z]+","").toLowerCase(), "drawable", getPackageName()));
							    	
									buttonPressFlags[0] = -1;
									buttonPressFlags[1] = -1;
									buttonPressFlags[2] = -1;
									buttonPressFlags[3] = -1;
									buttonPressFlags[4] = -1;
									buttonChangedFlags[3] = 1;
									newButton.setVisibility(View.GONE);
									suggestChampion.performClick();
								}
					        }
						});
					}
				}
				
				// suggested support champions
				ChampionAttributes[] suggestedSupport = teambuilder.suggestChampionsByStrategy(4);
				if ((suggestedSupport != null) && (buttonChangedFlags[4] == 0)){
					for (int i = 0; i < suggestedSupport.length; i++){
						final ImageButton newButton = initializeButton(new ImageButton(TeamBuilder.this), suggestedSupport[i]);
						if (suggestedSupport[i].getName().equals("NONAME")){
							newButton.setVisibility(View.GONE);
						}
						scroll5.addView(newButton);
						final ChampionAttributes currentChampion = suggestedSupport[i];
						newButton.setOnClickListener(new View.OnClickListener(){
							@Override
					        public void onClick(View v){
								if (buttonPressFlags[4] == 1){
									teambuilder.setOurTeam(4, currentChampion);
							    	String message = currentChampion.getName();
							    	support.setImageResource(getResources().getIdentifier(
							    			message.replaceAll("[^a-zA-Z]+","").toLowerCase(), "drawable", getPackageName()));

									buttonPressFlags[0] = -1;
									buttonPressFlags[1] = -1;
									buttonPressFlags[2] = -1;
									buttonPressFlags[3] = -1;
									buttonPressFlags[4] = -1;
									buttonChangedFlags[4] = 1;
									newButton.setVisibility(View.GONE);
									suggestChampion.performClick();
								}
					        }
						});
					}
				}
				
				// Table to display all available champions
				table.removeAllViews();
				
				// Calculate the screen diagonal in inches to identify it's a tablet or a phone.
				DisplayMetrics dm = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dm);
				int width=dm.widthPixels;
				int height=dm.heightPixels;
				int dens=dm.densityDpi;
				double wi=(double)width/(double)dens;
				double hi=(double)height/(double)dens;
				double x = Math.pow(wi,2);
				double y = Math.pow(hi,2);
				double screenInches = Math.sqrt(x+y);
				
				// Set the max num of buttons in a row according to the device size.
				int maxButtonInRow;
				if (screenInches < 6)
					maxButtonInRow = 5;		// A phone is less than 6 inches.
				else if (screenInches < 9)
					maxButtonInRow = 8;		// Small tablets are considered between 6 to 9 inches.
				else
					maxButtonInRow = 11;		// For big tablets (larger than 9 inches).
				
				int buttonInRow = maxButtonInRow;
				TableRow currentRow = null;
				ChampionAttributes[] allAvailableChampions = teambuilder.getAllAvailableChampions();
				for (int i = 0; i < allAvailableChampions.length; i++){
					if (buttonInRow == maxButtonInRow){
						buttonInRow = 0;
						TableRow newRow = new TableRow(TeamBuilder.this);
						newRow.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, 60));
						table.addView(newRow);
						currentRow = newRow;
					}
					final ImageButton newButton = initializeButton(new ImageButton(TeamBuilder.this), allAvailableChampions[i]);
					if (allAvailableChampions[i].getName().equals("NONAME")){
						newButton.setVisibility(View.GONE);
					}
					else{
						buttonInRow += 1;
					}
					currentRow.addView(newButton);
					
					// Following code works for selecting champion from "All available champion" field
					final ChampionAttributes currentChampion = allAvailableChampions[i];
					newButton.setOnClickListener(new View.OnClickListener(){
						@Override
				        public void onClick(View v){
							if (buttonPressFlags[0] == 1){
								teambuilder.setOurTeam(0, currentChampion);
								buttonChangedFlags[0] = 1;
						    	String message = currentChampion.getName();
						    	top.setImageResource(getResources().getIdentifier(
						    			message.replaceAll("[^a-zA-Z]+","").toLowerCase(), "drawable", getPackageName()));

							}
							else if (buttonPressFlags[1] == 1){
								teambuilder.setOurTeam(1, currentChampion);
								buttonChangedFlags[1] = 1;
						    	String message = currentChampion.getName();
						    	jungle.setImageResource(getResources().getIdentifier(
						    			message.replaceAll("[^a-zA-Z]+","").toLowerCase(), "drawable", getPackageName()));

							}
							else if (buttonPressFlags[2] == 1){
								teambuilder.setOurTeam(2, currentChampion);
								buttonChangedFlags[2] = 1;
						    	String message = currentChampion.getName();
						    	middle.setImageResource(getResources().getIdentifier(
						    			message.replaceAll("[^a-zA-Z]+","").toLowerCase(), "drawable", getPackageName()));

							}
							else if (buttonPressFlags[3] == 1){
								teambuilder.setOurTeam(3, currentChampion);
								buttonChangedFlags[3] = 1;
						    	String message = currentChampion.getName();
						    	adc.setImageResource(getResources().getIdentifier(
						    			message.replaceAll("[^a-zA-Z]+","").toLowerCase(), "drawable", getPackageName()));

							}
							else if (buttonPressFlags[4] == 1){
								teambuilder.setOurTeam(4, currentChampion);
								buttonChangedFlags[4] = 1;
						    	String message = currentChampion.getName();
						    	support.setImageResource(getResources().getIdentifier(
						    			message.replaceAll("[^a-zA-Z]+","").toLowerCase(), "drawable", getPackageName()));
							}
							buttonPressFlags[0] = -1;
							buttonPressFlags[1] = -1;
							buttonPressFlags[2] = -1;
							buttonPressFlags[3] = -1;
							buttonPressFlags[4] = -1;
							newButton.setVisibility(View.GONE);
							suggestChampion.performClick();
				        }
					});
				}
			}
		});
        
        // button Top
        top.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				buttonPressFlags[0] = 1;
				buttonChangedFlags[0] = 0;
				
				buttonPressFlags[1] = -1;
				buttonPressFlags[2] = -1;
				buttonPressFlags[3] = -1;
				buttonPressFlags[4] = -1;
				ChampionAttributes emptyChampion = new ChampionAttributes(); 
				teambuilder.setOurTeam(0, emptyChampion);
				top.setImageResource(R.drawable.top);
				suggestChampion.performClick();
			}
		});
        
        // button Jungle
        jungle.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				buttonPressFlags[1] = 1;
				buttonChangedFlags[1] = 0;
				
				buttonPressFlags[0] = -1;
				buttonPressFlags[2] = -1;
				buttonPressFlags[3] = -1;
				buttonPressFlags[4] = -1;
				ChampionAttributes emptyChampion = new ChampionAttributes(); 
				teambuilder.setOurTeam(1, emptyChampion);
				jungle.setImageResource(R.drawable.jungle);
				suggestChampion.performClick();
			}
		});
        
        // button Middle
        middle.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				buttonPressFlags[2] = 1;
				buttonChangedFlags[2] = 0;
				
				buttonPressFlags[0] = -1;
				buttonPressFlags[1] = -1;
				buttonPressFlags[3] = -1;
				buttonPressFlags[4] = -1;
				ChampionAttributes emptyChampion = new ChampionAttributes(); 
				teambuilder.setOurTeam(2, emptyChampion);
				middle.setImageResource(R.drawable.middle);
				suggestChampion.performClick();
			}
		});
        
        // button ADC
        adc.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				buttonPressFlags[3] = 1;
				buttonChangedFlags[3] = 0;
				
				buttonPressFlags[0] = -1;
				buttonPressFlags[1] = -1;
				buttonPressFlags[2] = -1;
				buttonPressFlags[4] = -1;
				ChampionAttributes emptyChampion = new ChampionAttributes(); 
				teambuilder.setOurTeam(3, emptyChampion);
				adc.setImageResource(R.drawable.adc);
				suggestChampion.performClick();
			}
		});
        
        // button Support
        support.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				buttonPressFlags[4] = 1;
				buttonChangedFlags[4] = 0;
				
				buttonPressFlags[0] = -1;
				buttonPressFlags[1] = -1;
				buttonPressFlags[2] = -1;
				buttonPressFlags[3] = -1;
				ChampionAttributes emptyChampion = new ChampionAttributes(); 
				teambuilder.setOurTeam(4, emptyChampion);
				support.setImageResource(R.drawable.support);
				suggestChampion.performClick();
			}	
		});
    }

	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
	// Initialization method for automatically generated imagebuttons.
    private ImageButton initializeButton(ImageButton icon, ChampionAttributes champion){
    	// First calculate how many pixels is 60 dip, which is the size of all imagebuttons.
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float dpInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, dm);
        
    	icon.setMaxHeight((int)dpInPx);
    	icon.setMaxWidth((int)dpInPx);
    	icon.setMinimumHeight((int)dpInPx);
    	icon.setMinimumWidth((int)dpInPx);
    	icon.setAdjustViewBounds(true);
    	icon.setScaleType(ScaleType.CENTER);
    	String message = champion.getName();
    	icon.setImageResource(getResources().getIdentifier(
    			message.replaceAll("[^a-zA-Z]+","").toLowerCase(), "drawable", getPackageName()));
    	return icon;
    }
}
