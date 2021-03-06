/*******************************************************************************
 * This file is part of Umbra.
 * 
 *     Umbra is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     Umbra is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with Umbra.  If not, see <http://www.gnu.org/licenses/>.
 * 
 *     Copyright (c) 2011 Vasile Jureschi <vasile.jureschi@gmail.com>.
 *     All rights reserved. This program and the accompanying materials
 *     are made available under the terms of the GNU Public License v3.0
 *     which accompanies this distribution, and is available at
 *     
 *    http://www.gnu.org/licenses/gpl-3.0.html
 * 
 *     Contributors:
 *        Vasile Jureschi <vasile.jureschi@gmail.com> - initial API and implementation
 ******************************************************************************/

package org.unchiujar.umbra.location;

import android.location.Location;

public class ApproximateLocation extends Location {

    private double mX;
    private double mY;
    private double mZ;

    public ApproximateLocation(String provider) {
        super(provider);
    }

    public ApproximateLocation(Location l) {
        super(l);
    }

    private void updateNVector() {
        mX = Math.cos(getLatitude()) * Math.cos(getLongitude());
        mY = Math.cos(getLatitude()) * Math.sin(getLongitude());
        mZ = Math.sin(getLatitude());
    }

    @Override
    public void setLatitude(double latitude) {
        super.setLatitude(latitude);
        updateNVector();
    }

    @Override
    public void setLongitude(double longitude) {
        super.setLongitude(longitude);
        updateNVector();
    }

    @Override
    public boolean equals(Object location) {
        // sanity checks
        if (!(location instanceof ApproximateLocation)) {
            return false;
        }
        // check if it is outside the preset radius
        return this.distanceTo((ApproximateLocation) location) <= LocationOrder.METERS_RADIUS;

    }

    // TODO - check theory behind implementation, see Effective Java
    @Override
    public int hashCode() {
        int randomPrime = 47;
        int result = 42;
        long hashLong = Double.doubleToLongBits(this.getLongitude());
        long hashLat = Double.doubleToLongBits(this.getLatitude());
        result = (int) (randomPrime * result + hashLong);
        result = (int) (randomPrime * result + hashLat);
        return result;
    }
}
