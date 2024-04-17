// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.gamer;

import java.sql.SQLException;
import com.google.gson.JsonObject;
import br.com.stenox.training.utils.JsonUtils;
import com.google.gson.JsonParser;
import br.com.stenox.training.gamer.tag.Tag;
import br.com.stenox.training.gamer.group.Group;
import java.sql.ResultSet;

public class GamerAdapter
{
    public Gamer read(final ResultSet rs) throws SQLException {
        final Gamer gamer = new Gamer(rs.getString("name"));
        gamer.setGroup(Group.fromString(rs.getString("group")));
        for (final String tags : rs.getString("tags").split(",")) {
            gamer.addTag(Tag.fromString(tags));
        }
        final JsonObject object = new JsonParser().parse(rs.getString("punishments")).getAsJsonObject();
        JsonUtils.setPunishments(gamer, object);
        return gamer;
    }
}
