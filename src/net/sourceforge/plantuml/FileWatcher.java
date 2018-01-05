/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2017, Arnaud Roques
 *
 * Project Info:  http://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
 * 
 * http://plantuml.com/patreon (only 1$ per month!)
 * http://plantuml.com/paypal
 * 
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 *
 * Original Author:  Arnaud Roques
 *
 * 
 */
package net.sourceforge.plantuml;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileWatcher {

	private final Map<File, Long> modified2 = new HashMap<>();

	public FileWatcher(Set<File> files) {
		if (files.isEmpty()) {
			throw new IllegalArgumentException();
		}
		for (File f : files) {
			modified2.put(f, f.lastModified());
		}
	}

	@Override
	public String toString() {
		return modified2.toString();
	}

	public boolean hasChanged() {
		for (Map.Entry<File, Long> ent : modified2.entrySet()) {
			final long nowModified = ent.getKey().lastModified();
			if (ent.getValue() != nowModified) {
				return true;
			}
		}
		return false;
	}

}
