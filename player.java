package client;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import core.Log;
import core.Manager;
import core.SQL;
import core.Service;
import core.Util;
import event_daily.ArenaTemplate;
import event_daily.ChienTruong;
import event_daily.Wedding;

import io.Message;
import io.Session;
import map.Dungeon;
import map.Map;
import map.MapService;
import map.Vgo;
import template.EffTemplate;
import template.Item3;
import template.Item47;
import template.ItemTemplate3;
import template.Kham_template;
import template.Level;
import template.LvSkill;
import template.Option;
import template.Part_fashion;
import template.Part_player;
import template.Pet_di_buon;
import template.Pet_di_buon_manager;
import template.Player_store;
import template.Skill;
import template.Member_ChienTruong;

public class Player extends Body2 {

    public List<EffTemplate> list_eff = new ArrayList<>();
    ;
    public boolean is_nhanban;
    public final Session conn;
//    public final int index;
    public boolean already_setup;
//    public String name;
    public Map map;
    public boolean is_changemap;
    public long timeCantChangeMap;
   
//    public short x;
//    public short y;
//    public short x_old;
//    public short y_old;
    public byte head;
    public byte eye;
    public byte hair;
//    public List<EffTemplate> list_eff;
    public Date date;
    public byte diemdanh;
    public byte chucphuc;
//    public int hieuchien;
    public byte type_exp;
//    public byte clazz;
//    public short level;
//    public long exp;
    private long vang;
    private int kimcuong;
//    public boolean isdie;
    public short tiemnang;
    public short kynang;
    public short point1;
    public short point2;
    public short point3;
    public short point4;
    public int suckhoe;
    public int pointarena;
//    public byte typepk;
    public int pointpk;
    public byte[] skill_point;
    public long[] time_delay_skill;
    public Body2 body;
    public byte maxbag;
    public byte maxbox;
    public Item item;
    public List<String> giftcode;
    public byte[][] rms_save;
    public List<Pet> mypet;
    public short pet_follow = -1;
    public List<Friend> list_friend;
    public List<String> list_enemies;
//    public int hp;
//    public int mp;
    public byte[] fashion;
    public Skill[] skills;
    public byte item_color_can_pick;
    public byte hp_mp_can_pick;
    public HashMap<Integer, Boolean> other_player_inside;
    public HashMap<Integer, Boolean> other_mob_inside;
    public HashMap<Integer, Boolean> other_mob_inside_update;
    public byte type_use_mount;
    public short id_item_rebuild;
    public boolean is_use_mayman;
    public short id_use_mayman;
    public short item_replace;
    public short item_replace2;
    public short id_buffer_126;
    public byte id_index_126;
    public Party party;
    public long time_use_poition_hp;
    public long time_use_poition_mp;
    public byte enough_time_disconnect;
    public int dame_affect_special_sk;
    public int hp_restore;
    public long time_buff_hp;
    public long time_buff_mp;
    public long time_affect_special_sk;
    public long time_speed_rebuild;
    public String name_trade;
    public short[] list_item_trade;
    public boolean lock_trade;
    public boolean accept_trade;
    public int money_trade;
//    public Dungeon dungeon;
    public Clan myclan;
    public byte id_medal_is_created;
    public short[] medal_create_material;
    public short fusion_material_medal_id;
    public long pet_atk_speed;
    public long time_eff_medal;
    public long time_eff_wear;
    public long time_eff_21;
    public long time_eff_22;
    public int[] point_active;
    public short id_horse;
    public boolean is_create_wing;
    public short id_remove_time_use;
    public byte id_wing_split;
    public byte[] in4_auto;
    public List<Player_store> my_store;
    public String my_store_name;
    public String Store_Sell_ToPL = "";
    public Pet_di_buon pet_di_buon;
    public short id_name;
    public String name_mem_clan_to_appoint = "";
    public byte id_select_mo_ly;
    public short id_hop_ngoc;
    public List<Item3> list_thao_kham_ngoc;
    public int time_atk_ngoc_hon_nguyen = 0;
    public short id_ngoc_tinh_luyen = -1;
    public long timeBlockCTG;
    public Wedding it_wedding;
    public String[] in4_wedding;
    public int[] quest_daily;
    public int chuyencan;
    public int jointx;
    public boolean tai;
    public boolean xiu;
    public String taixiu = "Bạn chưa đặt cược.";


    public void datatx() {
        if (this.tai == true) {
            this.taixiu = "Tài";
        }
        if (this.xiu == true) {
            this.taixiu = "Xỉu";
        }
        if (!this.tai && !this.xiu) {
            this.taixiu = "Bạn chưa đặt cược.";
        }
    }

    // kĩ năng mề
    public boolean isTangHinh;
    public long time_move;

    public boolean isDropMaterialMedal = true;
    public boolean isShowMobEvents = true;
    public ArenaTemplate PointArena;

    //kĩ năng khảm
//    public Kham_template kham;
    //create item star
    public boolean isCreateItemStar = false;
    public byte ClazzItemStar = -1;
    public byte TypeItemStarCreate = -1;
    public short[] MaterialItemStar;
    public int id_Upgrade_Medal_Star = -1;

    //biến heo chiến trường
    public long timeBienHeo;
    public long time_use_item_arena;
    public long time_henshin;
    public int id_henshin;

    public void ResetCreateItemStar() {
        isCreateItemStar = false;
        ClazzItemStar = -1;
        TypeItemStarCreate = -1;
    }

    public EffTemplate get_eff(int id) {
        for (int i = 0; i < list_eff.size(); i++) {
            EffTemplate temp = list_eff.get(i);
            if (temp.id == id) {
                return temp;
            }
        }
        return null;
    }

    public void update_point_arena(int i) throws IOException {
        Member_ChienTruong temp = ChienTruong.gI().get_infor_register(this.name);
        if (temp != null) {
            temp.point += i;
            this.pointarena += i;
            Service.send_health(this);
            Message m = new Message(-95);
            m.writer().writeByte(0);
            m.writer().writeShort(this.index);
            m.writer().writeShort(this.pointarena);
            this.conn.addmsg(m);
            m.cleanup();
        }
    }

    public void SetMaterialItemStar() {
        MaterialItemStar = new short[]{
            (short) Util.random(417, 437), (short) Util.random(437, 457), (short) Util.random(326, 336), (short) Util.random(336, 346), (short) Util.random(457, 464),
            (short) Util.random(417, 437), (short) Util.random(437, 457), (short) Util.random(326, 336), (short) Util.random(336, 346), (short) Util.random(457, 464),
            (short) Util.random(417, 437), (short) Util.random(437, 457), (short) Util.random(326, 336), (short) Util.random(336, 346), (short) Util.random(457, 464),
            (short) Util.random(417, 437), (short) Util.random(437, 457), (short) Util.random(326, 336), (short) Util.random(336, 346), (short) Util.random(457, 464),
            (short) Util.random(417, 437), (short) Util.random(437, 457), (short) Util.random(326, 336), (short) Util.random(336, 346), (short) Util.random(457, 464),
            (short) Util.random(417, 437), (short) Util.random(437, 457), (short) Util.random(326, 336), (short) Util.random(336, 346), (short) Util.random(457, 464),
            (short) Util.random(417, 437), (short) Util.random(437, 457), (short) Util.random(326, 336), (short) Util.random(336, 346), (short) Util.random(457, 464),
            (short) Util.random(417, 437), (short) Util.random(437, 457), (short) Util.random(326, 336), (short) Util.random(336, 346), (short) Util.random(457, 464),};
    }

    public void ChangeMaterialItemStar(byte type) {
        if (type >= 8) {
            return;
        }
        MaterialItemStar[type * 5] = (short) Util.random(417, 437);
        MaterialItemStar[type * 5 + 1] = (short) Util.random(437, 457);

        MaterialItemStar[type * 5 + 2] = (short) Util.random(326, 336);
        MaterialItemStar[type * 5 + 3] = (short) Util.random(336, 346);

        MaterialItemStar[type * 5 + 4] = (short) Util.random(457, 464);
    }

    public Player(Session conn, int id) {
        this.conn = conn;
        this.index = id;
        body = this;
        SetPlayer(this);
    }

    public void CheckSkillPoint() {
        for (int i = 0; i < skill_point.length; i++) {
            if (skill_point[i] <= 0) {
                continue;
            }
            LvSkill temp = skills[i].mLvSkill[skill_point[i] - 1];
            while (skill_point[i] > 0 && temp.LvRe > level) {
                temp = skills[i].mLvSkill[(--skill_point[i]) - 1];
                kynang++;
            }
        }
    }

    public boolean setup() throws IOException {
        long _time = System.currentTimeMillis();
        String query = "SELECT * FROM `player` WHERE `id` = '" + this.index + "' LIMIT 1;";
        try ( Connection connection = SQL.gI().getConnection();  Statement ps = connection.createStatement();  ResultSet rs = ps.executeQuery(query)) {
            if (!rs.next()) {
                return false;
            }
            //
            this.kham = new Kham_template();
            this.name = rs.getString("name");
            this.timeBlockCTG = rs.getLong("time_block_ctg");
            JSONArray jsar = (JSONArray) JSONValue.parse(rs.getString("body"));
            if (jsar == null) {
                return false;
            }
            head = Byte.parseByte(jsar.get(0).toString());
            eye = Byte.parseByte(jsar.get(1).toString());
            hair = Byte.parseByte(jsar.get(2).toString());
            jsar.clear();
            jsar = (JSONArray) JSONValue.parse(rs.getString("site"));
            if (jsar == null) {
                return false;
            }
            Map[] map_enter = Map.get_map_by_id(Byte.parseByte(jsar.get(0).toString()));
            if (map_enter != null) {
                x = Short.parseShort(jsar.get(1).toString());
                y = Short.parseShort(jsar.get(2).toString());
            } else {
                map_enter = Map.entrys.get(1);
                x = 432;
                y = 354;
            }
            map = map_enter[0];
            other_player_inside = new HashMap<>();
            other_mob_inside = new HashMap<>();
            other_mob_inside_update = new HashMap<>();
            jsar.clear();
            jsar = (JSONArray) JSONValue.parse(rs.getString("eff"));
            if (jsar == null) {
                return false;
            }
//            list_eff = new ArrayList<>();
            for (int i = 0; i < jsar.size(); i++) {
                JSONArray jsar2 = (JSONArray) JSONValue.parse(jsar.get(i).toString());
                if (jsar2 == null) {
                    return false;
                }
                this.body.add_EffDefault(Integer.parseInt(jsar2.get(0).toString()), Integer.parseInt(jsar2.get(1).toString()),
                        (System.currentTimeMillis() + Long.parseLong(jsar2.get(2).toString())));
//                list_eff.add(
//                        new EffTemplate(Integer.parseInt(jsar2.get(0).toString()), Integer.parseInt(jsar2.get(1).toString()),
//                                (System.currentTimeMillis() + Long.parseLong(jsar2.get(2).toString()))));
            }
            jsar.clear();
            date = Util.getDate(rs.getString("date"));
            diemdanh = rs.getByte("diemdanh");
            chucphuc = rs.getByte("chucphuc");
            hieuchien = rs.getInt("hieuchien");
             chuyencan = rs.getInt("chuyencan");
            type_exp = rs.getByte("typeexp");
            clazz = rs.getByte("clazz");
            level = rs.getShort("level");
            exp = rs.getLong("exp");
            //
            if (level > Manager.gI().lvmax) {
                level = (short) Manager.gI().lvmax;
                if (exp >= Level.entrys.get(level - 1).exp) {
                    exp = Level.entrys.get(level - 1).exp - 1;
                }
            }
            //
            vang = rs.getLong("vang");
            kimcuong = rs.getInt("kimcuong");
            isdie = false;
            tiemnang = rs.getShort("tiemnang");
            kynang = rs.getShort("kynang");
            point1 = rs.getShort("point1");
            point2 = rs.getShort("point2");
            point3 = rs.getShort("point3");
            point4 = rs.getShort("point4");
            pointarena = rs.getInt("point_arena");
            short it_name_ = rs.getShort("id_name");
            if (it_name_ != -1) {
                id_name = (short) (ItemTemplate3.item.get(it_name_).getPart() + 41);
                id_name
                        = (short) (((it_name_ >= 4720 && it_name_ <= 4727) || (it_name_ >= 4765 && it_name_ <= 4767)) ? id_name
                                : 78);
            } else {
                id_name = -1;
            }
            skill_point = new byte[21];
            time_delay_skill = new long[21];
            jsar = (JSONArray) JSONValue.parse(rs.getString("skill"));
            if (jsar == null) {
                return false;
            }
            for (int i = 0; i < 21; i++) {
                skill_point[i] = Byte.parseByte(jsar.get(i).toString());
                time_delay_skill[i] = 0;
            }
            jsar.clear();
            // load item

            maxbag = rs.getByte("maxbag");
            maxbox = 42;
            item = new Item(this);
            item.bag3 = new Item3[maxbag];
            item.box3 = new Item3[maxbag];
            item.wear = new Item3[24];
            item.bag47 = new ArrayList<>();
            item.box47 = new ArrayList<>();
            for (int i = 0; i < 24; i++) {
                item.wear[i] = null;
            }
            for (int i = 0; i < maxbag; i++) {
                item.bag3[i] = null;
                item.box3[i] = null;
            }
            jsar = (JSONArray) JSONValue.parse(rs.getString("item4"));
            if (jsar == null) {
                return false;
            }
            for (int i = 0; i < jsar.size(); i++) {
                JSONArray jsar2 = (JSONArray) JSONValue.parse(jsar.get(i).toString());
                Item47 temp = new Item47();
                temp.id = Short.parseShort(jsar2.get(0).toString());
                temp.quantity = Short.parseShort(jsar2.get(1).toString());
                temp.category = 4;
                if (temp.quantity > 0) {
                    item.bag47.add(temp);
                }
                jsar2.clear();
            }
            jsar.clear();
            jsar = (JSONArray) JSONValue.parse(rs.getString("item7"));
            if (jsar == null) {
                return false;
            }
            for (int i = 0; i < jsar.size(); i++) {
                JSONArray jsar2 = (JSONArray) JSONValue.parse(jsar.get(i).toString());
                Item47 temp = new Item47();
                temp.id = Short.parseShort(jsar2.get(0).toString());
                temp.quantity = Short.parseShort(jsar2.get(1).toString());
                temp.category = 7;
                if (temp.quantity > 0) {
                    item.bag47.add(temp);
                }
                jsar2.clear();
            }
            jsar.clear();
            jsar = (JSONArray) JSONValue.parse(rs.getString("item3"));
            if (jsar == null) {
                return false;
            }
            for (int i = 0; i < jsar.size(); i++) {
                JSONArray jsar2 = (JSONArray) JSONValue.parse(jsar.get(i).toString());
                Item3 temp = new Item3();
                temp.id = Short.parseShort(jsar2.get(0).toString());
                temp.clazz = Byte.parseByte(jsar2.get(1).toString());
                temp.type = Byte.parseByte(jsar2.get(2).toString());
                temp.level = Short.parseShort(jsar2.get(3).toString());
                temp.icon = Short.parseShort(jsar2.get(4).toString());
                temp.color = Byte.parseByte(jsar2.get(5).toString());
                temp.part = Byte.parseByte(jsar2.get(6).toString());
                temp.islock = Byte.parseByte(jsar2.get(7).toString()) == 1;
                temp.name = ItemTemplate3.item.get(temp.id).getName();
                if (temp.islock) {
                    temp.name += " [Khóa]";
                }
                temp.tier = Byte.parseByte(jsar2.get(8).toString());
                // if (temp.type == 15) {
                // temp.tier = 0;
                // }
                JSONArray jsar3 = (JSONArray) JSONValue.parse(jsar2.get(9).toString());
                temp.op = new ArrayList<>();
                for (int j = 0; j < jsar3.size(); j++) {
                    JSONArray jsar4 = (JSONArray) JSONValue.parse(jsar3.get(j).toString());
                    temp.op.add(
                            new Option(Byte.parseByte(jsar4.get(0).toString()), Integer.parseInt(jsar4.get(1).toString()), temp.id));
                }
                temp.time_use = 0;
                if (jsar2.size() >= 11) {
                    temp.time_use = Long.parseLong(jsar2.get(10).toString());
                }
                if (jsar2.size() >= 12) {
                    temp.tierStar = Byte.parseByte(jsar2.get(11).toString());
                }
                if (jsar2.size() >= 13) {
                    temp.expiry_date = Long.parseLong(jsar2.get(12).toString());
                }
                temp.UpdateName();
                if (temp.expiry_date == 0 || temp.expiry_date > _time) {
                    item.bag3[i] = temp;
                }
            }
            jsar.clear();
            jsar = (JSONArray) JSONValue.parse(rs.getString("itemwear"));
            if (jsar == null) {
                return false;
            }
            for (int i = 0; i < jsar.size(); i++) {
                JSONArray jsar2 = (JSONArray) JSONValue.parse(jsar.get(i).toString());
                if (jsar2 == null) {
                    return false;
                }
                Item3 temp = new Item3();
                temp.id = Short.parseShort(jsar2.get(0).toString());
                temp.name = ItemTemplate3.item.get(temp.id).getName() + " [Khóa]";
                temp.clazz = Byte.parseByte(jsar2.get(1).toString());
                temp.type = Byte.parseByte(jsar2.get(2).toString());
                temp.level = Short.parseShort(jsar2.get(3).toString());
                temp.icon = Short.parseShort(jsar2.get(4).toString());
                temp.color = Byte.parseByte(jsar2.get(5).toString());
                temp.part = Byte.parseByte(jsar2.get(6).toString());
                temp.tier = Byte.parseByte(jsar2.get(7).toString());
                // if (temp.type == 15) {
                // temp.tier = 0;
                // }
                temp.islock = true;
                JSONArray jsar3 = (JSONArray) JSONValue.parse(jsar2.get(8).toString());
                temp.op = new ArrayList<>();
                for (int j = 0; j < jsar3.size(); j++) {
                    JSONArray jsar4 = (JSONArray) JSONValue.parse(jsar3.get(j).toString());
                    if (jsar4 == null) {
                        return false;
                    }
                    temp.op.add(
                            new Option(Byte.parseByte(jsar4.get(0).toString()), Integer.parseInt(jsar4.get(1).toString()), temp.id));
                }
                Byte idx = Byte.parseB
