package ink.eccentric.domain.vo.common;

import com.alibaba.fastjson2.annotation.JSONField;
import ink.eccentric.domain.po.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author eccentric
 */
@Data
@NoArgsConstructor
@ToString
public class UserDetailsImpl implements UserDetails {
    private User user;

    private List<String> permissions;

    public UserDetailsImpl(User user,List<String> permissions){
        this.user = user;
        this.permissions = permissions;
    }

    @JSONField(serialize = false,deserialize = false)
    private List<GrantedAuthority> authorities;
    @Override
    @JSONField(serialize = false,deserialize = false)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (Objects.nonNull(authorities)){
            return authorities;
        }
        authorities = new ArrayList<>();
        for (String permission : permissions){
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return authorities;
    }

    @Override
    @JSONField(serialize = false,deserialize = false)
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    @JSONField(serialize = false,deserialize = false)
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    @JSONField(serialize = false,deserialize = false)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JSONField(serialize = false,deserialize = false)
    public boolean isAccountNonLocked() {
        return !Boolean.getBoolean(user.getStatus());
    }

    @Override
    @JSONField(serialize = false,deserialize = false)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JSONField(serialize = false,deserialize = false)
    public boolean isEnabled() {
        return !Boolean.getBoolean(user.getStatus());
    }
}
