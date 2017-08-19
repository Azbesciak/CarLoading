package com.witkups.carloading.entity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class Host {
    private String id;
    private int length;
    private int width;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Host host = (Host) o;

        return id != null ? id.equals(host.id) : host.id == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
