package com.example.climateSim;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class QuizHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "mathsone";
    // tasks table name
    private static final String TABLE_QUEST = "quest";
    // tasks Table Columns names
    private static final String KEY_ID = "qid";
    private static final String KEY_QUES = "question";
    private static final String KEY_ANSWER = "answer"; // correct option
    private static final String KEY_OPTA = "opta"; // option a
    private static final String KEY_OPTB = "optb"; // option b

    private SQLiteDatabase dbase;

    public QuizHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase = db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
                + " TEXT, " + KEY_ANSWER + " TEXT, " + KEY_OPTA + " TEXT, "
                + KEY_OPTB + " TEXT)";
        db.execSQL(sql);
        addQuestion();
        // db.close();
    }

    private void addQuestion() {
        Question q1 = new Question("Which of these is not a soil condition indicating high risk" +
                " of a flash flood during a rainstorm?",
                "When the soil is saturated and will not permit additional rainfall to infiltrate",
                "When the soil is sandy with wider spaces between particles",
                "When the soil is sandy with wider spaces between particles");
        //Using precipitation data from satellites such as the Global Precipitation Measurement
        // mission, plus soil moisture data from the upcoming Soil Moisture Active Passive (SMAP)
        // mission, allows scientists to more accurately model potential flood conditions. In
        // addition, the instruments onboard SMAP will have difficulty obtaining a clear observation
        // of the soil surface when it is raining, which is expected to cause errors in the SMAP
        // soil moisture retrievals. The addition of GPM data to the SMAP soil moisture algorithm
        // will provide a more robust approach for assessing the times and locations of rainfall
        // events so that the SMAP mission can correctly interpret the soil moisture data.
        this.addQuestion(q1);

        Question q2 = new Question("Most of the Earth's water is found in:",
                "Lakes and rivers",
                "The ocean",
                "The ocean");
        //Most of Earth's water (96.5 percent) is saline, or salty, ocean seawater. Most of the
        // freshwater is tied up in glaciers and ice sheets, which means that only a relatively
        // small portion is available to sustain human, plant and animal life.
        this.addQuestion(q2);

        Question q3 = new Question("Which of these has a greater effect on global sea level?",
                "Melting sea ice",
                "Melting land ice",
                "Melting land ice");
        //Sea level rise is caused primarily by two factors related to global warming: the added
        // water from melting land ice and the expansion of seawater as it warms. Melting sea ice
        // does not contribute nearly as much to ocean water volume because floating ice displaces
        // water in an amount equal to its volume. Some coastal regions and island nations have
        // begun to experience the consequences of sea level rise. Rising seas not only affect
        // dwellings and structures, but fresh water is also affected as rising saltwater begins
        // to contaminate it through "saltwater intrusion."
        this.addQuestion(q3);

        Question q4 = new Question("What is sea surface salinity?",
                "the light reflected off of the ocean's surface",
                "Salt concentration on the ocean's surface",
                "Salt concentration on the ocean's surface");
        this.addQuestion(q4);
        //NASA's Aquarius satellite measures sea surface salinity from space. The concentration
        // of salt on the ocean's surface — the part of the ocean that actively exchanges water
        // and heat with Earth's atmosphere — is a critical driver of ocean processes and climate
        // variability. To better understand the regional and global processes that link
        // variations in ocean salinity to changes in the global water cycle — and how these
        // variations influence ocean circulation and climate — NASA built and launched Aquarius,
        // the primary instrument aboard the international Aquarius/Satélite de Aplicaciones
        // Científicas (SAC)-D observatory.


        Question q5 = new Question("Tides are caused by:",
                "The wind",
                "The gravitational pull of both the moon and the sun",
                "The gravitational pull of both the moon and the sun");
        //Both the sun and the moon influence tide rise and fall, which are caused by the
        // gravitational attraction between the ocean and the moon as well as the ocean and the
        // sun. "Centrifugal force" — the same force that pulls you backward on the teacup ride
        // at Disneyland – is generated from Earth’s spin and is another factor that influences
        // Earth's tides.
        this.addQuestion(q5);

        Question q6 = new Question("Sea level isn’t level at all. The ocean has hills and " +
                "valleys similar to what we see on the land's surface. Ocean surface highs and " +
                "lows are known as:\n",
                "Ocean surface topography",
                "Ocean surface geography",
                "Ocean surface topography");
        //"Topography" is the shape of a surface, including its relief. Sea surface topography is
        // influenced by both gravity and ocean circulation. Accurate gravity data are critical for
        // determining large- and medium-scale ocean circulation patterns.
        this.addQuestion(q6);

        Question q7 = new Question("The only current that makes an uninterrupted circle around " +
                "the entire Earth without hitting land is the:",
                "Antarctic Circumpolar Current",
                "Gulf Stream",
                "Antarctic Circumpolar Current");
        //Ocean currents are driven by ocean winds and influenced by the "Coriolis effect." Water
        // flows along the current until it encounters land, where the Coriolis effect causes the
        // current to rotate clockwise in the northern hemisphere and counterclockwise in the
        // south. Most currents form "gyres," or large systems of rotating ocean currents. The
        // gyre’s rotational patterns draw in debris from across the ocean, while wind patterns
        // push the trash toward the center, trapping it there. In the Southern Ocean around
        // Antarctica, there is no land, which allows the Antarctic Circumpolar Current to travel
        // around the globe uninterrupted.
        this.addQuestion(q7);

        Question q8 = new Question("What are aerosols?",
                "Another name for greenhouse gases like carbon dioxide and methane",
                "Minute particles suspended in the atmosphere",
                "Minute particles suspended in the atmosphere");
        //Aerosols range in size from smaller than the smallest virus to about the diameter of a
        // human hair and can remain aloft anywhere from a few days to years.
        this.addQuestion(q8);

        Question q9 = new Question("Most aerosols in the atmosphere are from human-caused pollution.",
                "True",
                "False",
                "False");
        //Most occur naturally, including sea salt, desert dust, wildfire smoke and sulfates from
        // volcanic eruptions. A significant minority is from human causes such as industrial
        // pollution, cars and deliberate burning of trees and agricultural waste. But the
        // artificial stuff can dominate the air downwind of urban and industrial areas.
        this.addQuestion(q9);

        Question q10 = new Question("Which of these qualities are used to identify types of clouds?",
                "Shape, altitude and whether they are producing precipitation",
                "Color and how fast they are moving through the sky",
                "Shape, altitude and whether they are producing precipitation");
        //Clouds occur in three basic shapes: puffy (cumulus), layered (stratus) and wispy
        // (cirrus). They are also identified by the altitudes at which they form. Clouds below
        // two kilometers (about one mile) are considered low. Those from two to six kilometers
        // (about one to four miles) are considered mid-level, while those above that altitude
        // are considered high. And finally, the prefix “nimbo-“ and postfix “-nimbus” signify
        // clouds from which precipitation is falling.
        this.addQuestion(q10);

        Question q11= new Question("Do clouds heat or cool the earth?",
                "Heat",
                "Both heat and cool",
                "Both heat and cool");
        //Different kinds of clouds at different altitudes play different roles in regulating
        // Earth’s temperature. Wispy clouds at high altitudes tend to trap infrared radiation
        // emitted by the sun-warmed Earth, which prevents it from escaping into space and warms
        // the atmosphere. On the other hand, thick, low-lying clouds tend to cool the planet by
        // shading Earth’s surface, reflecting sunlight back into space. Scientists are trying
        // to determine how global warming will affect the balance we’ve been enjoying up to now.
        this.addQuestion(q11);

        Question q12 = new Question("About how much of Earth’s surface is covered by clouds at any given time?",
                "30 percent",
                "60 percent",
                "60 percent");
        //Covering most of the Earth at any given moment, clouds have a huge—but
        // insufficiently understood—impact on climate.
        this.addQuestion(q12);

        Question q13 = new Question("The brighter a cloud looks, the less pollution it contains.",
                "True",
                "False",
                "False");
        //A given volume of pollution-rich cloud tends to have more numerous and smaller droplets
        // than in more pristine clouds. The high number of small droplets provides more surfaces
        // to reflect light, making the cloud appear brighter than a cloud consisting of fewer and
        // larger droplets.
        this.addQuestion(q13);

        Question q14 = new Question("For how long do dust and soot generally remain in the air?",
                "1 day",
                "10 days",
                "10 days");
        //On average, this is how long it takes for dust, soot and water-soluble chemicals to be
        // washed out of the atmosphere by falling rain.
        this.addQuestion(q14);

        Question q15 = new Question("How far can air pollution travel from its source?",
                "To the next state or major region",
                "Around the world",
                "Around the world");
        //From space, we can see pollution moving across the oceans from one continent to another.
        // For example, vast quantities of industrial aerosols (microscopic particles suspended in
        // the atmosphere) and smoke from events such as wood or vegetation burning can travel from
        // one side of the globe to another.
        this.addQuestion(q15);

        Question q16 = new Question("In the 10,000 years before the Industrial Revolution in " +
                "1751, carbon dioxide levels rose less than 1 percent. Since then, they've risen by:",
                "37 percent",
                "11 percent",
                "37 percent");
        //From 1751-2003, humans added 466 billion tons of carbon to the atmosphere in the form of
        // carbon dioxide. Eighty-five percent of all human-produced carbon dioxide comes from
        // burning coal, natural gas, oil and gasoline.
        this.addQuestion(q16);

        Question q17 = new Question("Burning fossil fuels is the major source of human-produced" +
                " carbon dioxide. But what is the second leading source?",
                "Cow flatulence",
                "Deforestation",
                "Deforestation");
        //Forest clearing produces about a fifth of current carbon dioxide emissions. Interestingly,
        // cows, which produce massive amounts of methane (also a potent greenhouse gas), are
        // becoming a cause for concern in countries with large cattle industries.
        this.addQuestion(q17);

        Question q18 = new Question("We produce more than 30 billion tons of carbon dioxide per" +
                " year. Where does the majority of it end up?",
                "It lingers in the atmosphere.",
                "It enters our oceans.",
                "It lingers in the atmosphere.");
        //Forty to 50 percent of the carbon dioxide stays in the air, and 30 percent is dissolved
        // in the oceans. Scientists are not sure about the rest. They believe it is absorbed by
        // forests, soil and crops.
        this.addQuestion(q18);

        Question q19 = new Question("If humans stopped emitting carbon dioxide tomorrow, what " +
                "would happen to global temperatures?",
                "They would immediately begin to drop.",
                "They would continue to rise.",
                "They would continue to rise.");
        //Even if we stopped all carbon emissions right now, the hundreds of billions of tons of
        // carbon dioxide that have been pumped into the atmosphere and absorbed by the oceans
        // since the Industrial Revolution would continue to warm the planet. For how long? No one
        // knows for sure, but estimates range from hundreds of years to thousands of years into
        // the future.
        this.addQuestion(q19);

        Question q20 = new Question("Which ocean is the saltiest?",
                "Atlantic",
                "Indian",
                "Atlantic");
        //Surface waters in the Atlantic have the highest salinity, higher than 37 parts per
        // thousand in some areas. This is because, on average, there is more evaporation than
        // combined rainfall and river runoff into the Atlantic Ocean, maintaining higher salinity
        // than in the other basins.
        this.addQuestion(q20);

        // END
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
        // Create tables again
        onCreate(db);
    }

    // Adding new question
    public void addQuestion(Question quest) {
        // SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUES, quest.getQUESTION());
        values.put(KEY_ANSWER, quest.getANSWER());
        values.put(KEY_OPTA, quest.getOPTA());
        values.put(KEY_OPTB, quest.getOPTB());

        // Inserting Row
        dbase.insert(TABLE_QUEST, null, values);
    }

    public List<Question> getAllQuestions() {
        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        dbase = this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setID(cursor.getInt(0));
                quest.setQUESTION(cursor.getString(1));
                quest.setANSWER(cursor.getString(2));
                quest.setOPTA(cursor.getString(3));
                quest.setOPTB(cursor.getString(4));

                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }

}
