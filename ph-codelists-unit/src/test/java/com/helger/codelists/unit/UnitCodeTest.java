/*
 * Copyright (C) 2026 Philip Helger
 * http://www.helger.com
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.codelists.unit;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

import com.helger.unittest.support.TestHelper;

/**
 * Test class for class {@link UnitCode}.
 *
 * @author Philip Helger
 */
public final class UnitCodeTest
{
  @Test
  public void testBasic ()
  {
    final UnitCode aSut = new UnitCode ("KGM", EUnitCodeSource.REC20, "kilogram");
    assertEquals ("KGM", aSut.getID ());
    assertEquals (EUnitCodeSource.REC20, aSut.getSource ());
    assertEquals ("kilogram", aSut.getDisplayName ());

    TestHelper.testDefaultImplementationWithEqualContentObject (aSut,
                                                                new UnitCode ("KGM",
                                                                              EUnitCodeSource.REC20,
                                                                              "kilogram"));
    TestHelper.testDefaultImplementationWithDifferentContentObject (aSut,
                                                                    new UnitCode ("GRM",
                                                                                  EUnitCodeSource.REC20,
                                                                                  "gram"));
    TestHelper.testDefaultImplementationWithDifferentContentObject (aSut,
                                                                    new UnitCode ("KGM",
                                                                                  EUnitCodeSource.REC21,
                                                                                  "kilogram"));
  }

  @Test
  public void testTranslation ()
  {
    // Texts come from ph-codelists-unit_de.properties
    final UnitCode aSut = new UnitCode ("KGM", EUnitCodeSource.REC20, "kilogram");
    assertEquals ("Kilogramm", aSut.getDisplayText (Locale.GERMAN));
    assertEquals ("Kilogramm", aSut.getDisplayText (Locale.GERMANY));
    assertEquals ("kilogram", aSut.getDisplayText (Locale.FRENCH));
  }
}
