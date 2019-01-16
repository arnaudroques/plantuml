/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2020, Arnaud Roques
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
package net.sourceforge.plantuml.sequencediagram.command;

import net.sourceforge.plantuml.StringUtils;
import net.sourceforge.plantuml.command.CommandExecutionResult;
import net.sourceforge.plantuml.command.SingleLineCommand2;
import net.sourceforge.plantuml.command.regex.RegexConcat;
import net.sourceforge.plantuml.command.regex.RegexLeaf;
import net.sourceforge.plantuml.command.regex.RegexResult;
import net.sourceforge.plantuml.graphic.HtmlColor;
import net.sourceforge.plantuml.sequencediagram.LifeEventType;
import net.sourceforge.plantuml.sequencediagram.Participant;
import net.sourceforge.plantuml.sequencediagram.SequenceDiagram;

public class CommandActivate extends SingleLineCommand2<SequenceDiagram> {

	public CommandActivate() {
		super(getRegexConcat());
	}

	static RegexConcat getRegexConcat() {
		return new RegexConcat(new RegexLeaf("^"), //
				new RegexLeaf("TYPE", "(activate|deactivate|destroy|create)"), //
				new RegexLeaf("[%s]+"), //
				new RegexLeaf("WHO", "([\\p{L}0-9_.@]+|[%g][^%g]+[%g])"), //
				new RegexLeaf("[%s]*"), //
				new RegexLeaf("BACK", "(#\\w+)?"), //
				new RegexLeaf("LINE", "(?:[%s]+(#\\w+))?"), //
				new RegexLeaf("$"));
	}

	@Override
	protected CommandExecutionResult executeArg(SequenceDiagram diagram, RegexResult arg) {
		final LifeEventType type = LifeEventType.valueOf(StringUtils.goUpperCase(arg.get("TYPE", 0)));
		final Participant p = diagram.getOrCreateParticipant(StringUtils
				.eventuallyRemoveStartingAndEndingDoubleQuote(arg.get("WHO", 0)));
		final HtmlColor backColor = diagram.getSkinParam().getIHtmlColorSet().getColorIfValid(arg.get("BACK", 0));
		final HtmlColor lineColor = diagram.getSkinParam().getIHtmlColorSet().getColorIfValid(arg.get("LINE", 0));
		final String error = diagram.activate(p, type, backColor, lineColor);
		if (error == null) {
			return CommandExecutionResult.ok();
		}
		return CommandExecutionResult.error(error);
	}

}
