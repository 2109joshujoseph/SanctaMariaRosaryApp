package com.joeinitiatives.rosary;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Kreupasanam Mathavu — Kreupasanam Marian Shrine, Kalavoor, Alappuzha.
 * Features the Morning 5:30 AM Apparition Prayer and the Evening Marian Covenant Prayer.
 */
public class KreupasanamScreen extends BaseScreen {

    public KreupasanamScreen(MainActivity a) {
        super(a, "kreupasanam", true);
    }

    @Override
    protected void buildContent() {
        LinearLayout col = scrollBody(18);

        // Shrine Art Image
        col.addView(Ui.imageName(act, "kreupasanam", 220, 22));

        // Header Card
        LinearLayout head = Ui.column(act);
        head.setPadding(Ui.dp(act, 18), Ui.dp(act, 16), Ui.dp(act, 18), Ui.dp(act, 18));
        head.setBackground(Ui.stroke(act, 0xFFFDFAF2, 0x44C9A227, 22));

        LinearLayout topPills = Ui.row(act);
        topPills.addView(Widgets.pill(act, "Marian Shrine · Kalavoor", Ui.BLUE_MID, Ui.BLUE_LIGHT));
        head.addView(topPills);

        TextView title = Ui.text(act, "Kreupasanam Mathavu", 26, Ui.BLUE, true);
        title.setTypeface(Ui.serifBold());
        title.setLineSpacing(Ui.dp(act, 3), 1.07f);
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tlp.topMargin = Ui.dp(act, 8);
        head.addView(title, tlp);

        TextView intro = Ui.text(act,
                "Kreupasanam is a Marian retreat centre and pilgrimage shrine at Kalavoor in Alappuzha district, Kerala. " +
                "The name means 'Seat of Grace.' Pilgrims come to pray before the image of Kreupasanam Mathavu, seeking " +
                "reconciliation, healing, family peace, and spiritual fulfillment through the Marian Covenant.",
                13.5f, Ui.INK, false);
        intro.setLineSpacing(Ui.dp(act, 5), 1.16f);
        LinearLayout.LayoutParams ilp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ilp.topMargin = Ui.dp(act, 9);
        head.addView(intro, ilp);
        col.addView(head);

        // --- MORNING PRAYER (5:30 AM) ---
        col.addView(Widgets.section(act, "Morning Prayer (5:30 AM)"));

        LinearLayout morningCard = Widgets.card(act);
        morningCard.setBackground(Ui.stroke(act, 0xFFFDFAF3, 0x55C9A227, 20));

        LinearLayout mTop = Ui.row(act);
        mTop.addView(Widgets.pill(act, "Morning 5:30 AM", Ui.GOLD, Ui.GOLD_PALE));
        TextView mSubtitle = Ui.text(act, "Apparition Gratitude & Petition", 11.5f, Ui.MUTED, false);
        mSubtitle.setPadding(Ui.dp(act, 8), 0, 0, 0);
        mTop.addView(mSubtitle);
        morningCard.addView(mTop);

        Widgets.gap(morningCard, 10);

        String morningPrayer =
                "Mother, Holy Mother, the Mother of God, please have our heartfelt gratitude for all the blessings " +
                "and graces received through Kreupasanam by us and our country by Mother's most amazingly powerful intercession.\n\n" +
                "Mother, Holy Mother, the Mother of God, may the study report on your apparition, prophesies or miracles at Kalavoor, " +
                "Kreupasanam Marian Retreat Centre, which occurred in the year 2004 – on 7th December, 2.30 P.M. in the afternoon, " +
                "may get ecclesiastical approval after having studied and examined the things that fall or relate to question of facts, " +
                "evidence and above all the question of theology, so that the fact about the loving, concern and affectionate care of " +
                "Blessed Mother manifested in the Kreupasanam apparition, as the protector and the defender of all those who are in " +
                "unnecessary sufferings and degradation across our world, and Mother as the Queen of the Prophets, The Ark of the Covenant, " +
                "The Mother of Heavens and earth and The comforter of afflicted, the devotion to the Mother Mary may become the universal " +
                "catholic as including all, embracing all, concluding so that all those in the margins of society may recognize Christ in " +
                "their painful stories by witnessing to the Good News of justice and compassion, so that our families may become better " +
                "families, fuller families free from want, fear, disease and above all sin.\n\n" +
                "Mother, Holy Mother, the Queen of Heaven and Earth, may the Holy Catholic Church dedicate the entire universe with its cosmic " +
                "process to you, O Holy Mother, to your Kreupasanam apparition so we pray. Holy Mother, the Mother of Rosary, in union with all " +
                "the prayers of Kreupasanam, the intentions of this family be granted (mention the intention or the prayer request) in this blessed " +
                "and joyful moment of thy apparition. Amen.";

        TextView mpv = Ui.prayerBody(act, morningPrayer, Ui.INK);
        morningCard.addView(mpv);

        Widgets.gap(morningCard, 12);

        // Recitation instructions box
        LinearLayout mRecite = Ui.column(act);
        mRecite.setPadding(Ui.dp(act, 14), Ui.dp(act, 10), Ui.dp(act, 14), Ui.dp(act, 10));
        mRecite.setBackground(Ui.bg(act, Ui.GOLD_PALE, 14));
        TextView mReciteText = Ui.text(act, "Recite: 1 Credo, 7 Our Father, 7 Hail Mary", 12.5f, Ui.BLUE, true);
        mReciteText.setTypeface(Ui.serifBold());
        mRecite.addView(mReciteText);
        morningCard.addView(mRecite);

        col.addView(morningCard);

        // --- EVENING PRAYER (MARIAN COVENANT) ---
        col.addView(Widgets.section(act, "Evening Prayer (Marian Covenant)"));

        LinearLayout eveCard = Widgets.card(act);
        eveCard.setBackground(Ui.stroke(act, 0xFFFDFAF3, 0x55C9A227, 20));

        LinearLayout eTop = Ui.row(act);
        eTop.addView(Widgets.pill(act, "Evening Prayer", Ui.BLUE_MID, Ui.BLUE_LIGHT));
        TextView eSub = Ui.text(act, "Kreupasanam Covenant Prayer", 11.5f, Ui.MUTED, false);
        eSub.setPadding(Ui.dp(act, 8), 0, 0, 0);
        eTop.addView(eSub);
        eveCard.addView(eTop);

        Widgets.gap(eveCard, 8);

        TextView candleNote = Ui.text(act, "(After Evening Family Prayer · Light Candle Prayer at Kreupasanam Marian Shrine)", 11.5f, Ui.MUTED, false);
        candleNote.setTypeface(Ui.sansMedium());
        eveCard.addView(candleNote);

        Widgets.gap(eveCard, 12);

        // 1st Petition
        TextView pet1 = Ui.prayerBody(act,
                "Holy Mother, the Kreupasanam Mother of Grace, please empower me to fulfill the Marian Covenant Prayer which I oathed in thy holy presence. " +
                "My prayer requests kindly be granted with all those who participate in the online Marian Covenant Prayer with their intentions.", Ui.INK);
        eveCard.addView(pet1);
        TextView dec1 = Ui.text(act, "▸ Recite: 1 Our Father, 1 Hail Mary", 12f, Ui.GOLD, true);
        dec1.setTypeface(Ui.serifBold());
        dec1.setPadding(0, Ui.dp(act, 4), 0, Ui.dp(act, 10));
        eveCard.addView(dec1);

        // 2nd Petition
        TextView pet2 = Ui.prayerBody(act,
                "Holy Mother, the Kreupasanam Mother of Grace, please help our family to fulfill the covenant we had made in thy presence. " +
                "With the intentions of those who participate in the Monday and Tuesday Marian Covenant Retreats, let my family's intentions also be granted.", Ui.INK);
        eveCard.addView(pet2);
        TextView dec2 = Ui.text(act, "▸ Recite: 1 Our Father, 1 Hail Mary", 12f, Ui.GOLD, true);
        dec2.setTypeface(Ui.serifBold());
        dec2.setPadding(0, Ui.dp(act, 4), 0, Ui.dp(act, 10));
        eveCard.addView(dec2);

        // 3rd Petition
        TextView pet3 = Ui.prayerBody(act,
                "Holy Mother, the Kreupasanam Mother of Grace, please help me to comply with the covenant I had made before you, O Holy Mother, for my family. " +
                "With the prayer intentions of all those who participate in the residential Marian retreat and second Saturday Marian Covenant retreat at Kreupasanam, " +
                "let my family's prayer requests also be kindly granted.", Ui.INK);
        eveCard.addView(pet3);
        TextView dec3 = Ui.text(act, "▸ Recite: 1 Our Father, 1 Hail Mary", 12f, Ui.GOLD, true);
        dec3.setTypeface(Ui.serifBold());
        dec3.setPadding(0, Ui.dp(act, 4), 0, Ui.dp(act, 10));
        eveCard.addView(dec3);

        Widgets.gap(eveCard, 6);

        // Final recitation
        LinearLayout eRecite = Ui.column(act);
        eRecite.setPadding(Ui.dp(act, 14), Ui.dp(act, 10), Ui.dp(act, 14), Ui.dp(act, 10));
        eRecite.setBackground(Ui.bg(act, Ui.GOLD_PALE, 14));
        TextView eReciteText = Ui.text(act, "Say: 1 Credo, 1 Our Father & 1 Hail Mary after the Covenant Prayer", 12.5f, Ui.BLUE, true);
        eReciteText.setTypeface(Ui.serifBold());
        eRecite.addView(eReciteText);
        eveCard.addView(eRecite);

        col.addView(eveCard);

        // --- SHRINE COVENANT INTENTIONS ---
        col.addView(Widgets.section(act, "Covenant Intentions"));
        String[] intentions = {
                "For healing of body, mind, and spirit",
                "For peace and reconciliation in divided families",
                "For those facing financial hardship, debt, and unemployment",
                "For children and youth to grow in sanctity and faith",
                "For the sick, the lonely, the suffering, and the dying",
                "In heartfelt thanksgiving for graces and miracles received"
        };
        for (int i = 0; i < intentions.length; i++) {
            LinearLayout row = Ui.row(act);
            row.setPadding(Ui.dp(act, 14), Ui.dp(act, 11), Ui.dp(act, 14), Ui.dp(act, 11));
            row.setBackground(Ui.stroke(act, 0xFFFDFAF3, 0x2AC9A227, 16));
            TextView num = Ui.text(act, String.format(java.util.Locale.US, "%02d", i + 1), 11, Ui.GOLD, true);
            num.setTypeface(Ui.serifBold());
            num.setLayoutParams(new LinearLayout.LayoutParams(Ui.dp(act, 28), ViewGroup.LayoutParams.WRAP_CONTENT));
            row.addView(num);
            TextView item = Ui.text(act, intentions[i], 13.5f, Ui.INK, false);
            LinearLayout.LayoutParams itlp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            itlp.leftMargin = Ui.dp(act, 4);
            row.addView(item, itlp);
            col.addView(row);
            Widgets.gap(col, 7);
        }

        Widgets.gap(col, 20);
    }
}
