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
package net.sourceforge.plantuml.creole;

import net.sourceforge.plantuml.command.regex.Matcher2;
import net.sourceforge.plantuml.command.regex.MyPattern;
import net.sourceforge.plantuml.command.regex.Pattern2;
import net.sourceforge.plantuml.graphic.FontConfiguration;
import net.sourceforge.plantuml.graphic.FontPosition;

public class CommandCreoleExposantChange implements Command {

	private final Pattern2 pattern;
	private final FontPosition position;

	private CommandCreoleExposantChange(String p, FontPosition position) {
		this.pattern = MyPattern.cmpile(p);
		this.position = position;
	}

	public static Command create(FontPosition position) {
		return new CommandCreoleExposantChange("^(?i)(" + "\\<" + position.getHtmlTag() + "\\>" + "(.*?)\\</"
				+ position.getHtmlTag() + "\\>)", position);
	}

	// public static Command createLegacyEol(FontStyle style) {
	// return new CommandCreoleExposantChange("^(" + style.getActivationPattern() + "(.+))$", style);
	// }

	public int matchingSize(String line) {
		final Matcher2 m = pattern.matcher(line);
		if (!m.find()) {
			return 0;
		}
		return m.group(2).length();
	}

	public String executeAndGetRemaining(String line, StripeSimple stripe) {
		final Matcher2 m = pattern.matcher(line);
		if (!m.find()) {
			throw new IllegalStateException();
		}
		final FontConfiguration fc1 = stripe.getActualFontConfiguration();
		final FontConfiguration fc2 = fc1.changeFontPosition(position);

		stripe.setActualFontConfiguration(fc2);
		stripe.analyzeAndAdd(m.group(2));
		stripe.setActualFontConfiguration(fc1);
		return line.substring(m.group(1).length());
	}

}
