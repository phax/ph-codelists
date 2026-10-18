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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

import com.helger.collection.commons.ICommonsList;

/**
 * Test class for class {@link UnitCodes}.
 *
 * @author Philip Helger
 */
public final class UnitCodesTest
{
  @Test
  public void testBasic ()
  {
    assertEquals (2162, UnitCodes.getCount ());

    final IUnitCode aOne = UnitCodes.getFromIDOrNull ("C62");
    assertNotNull (aOne);
    assertEquals ("C62", aOne.getID ());
    assertEquals ("one", aOne.getDisplayName ());
    assertSame (EUnitCodeSource.REC20, aOne.getSource ());

    final IUnitCode aPiece = UnitCodes.getFromIDOrNull ("XPP");
    assertNotNull (aPiece);
    assertSame (EUnitCodeSource.REC21, aPiece.getSource ());

    assertTrue (UnitCodes.containsID ("KGM"));
    assertFalse (UnitCodes.containsID ("kgm"));
    assertFalse (UnitCodes.containsID (null));
    assertNull (UnitCodes.getFromIDOrNull ("does-not-exist"));
  }

  @Test
  public void testGetAllIsACopy ()
  {
    final ICommonsList <IUnitCode> aAll = UnitCodes.getAll ();
    assertEquals (UnitCodes.getCount (), aAll.size ());
    aAll.clear ();
    assertEquals (UnitCodes.getCount (), UnitCodes.getAll ().size ());
  }

  @Test
  public void testDisplayTextFallsBackToEnglish ()
  {
    final IUnitCode aKilogram = UnitCodes.getFromIDOrNull ("KGM");
    assertNotNull (aKilogram);
    // The library ships German only
    for (final Locale aLocale : new Locale [] { Locale.ENGLISH, Locale.US, Locale.FRENCH, Locale.ROOT })
      assertEquals ("kilogram", aKilogram.getDisplayText (aLocale));
  }

  @Test
  public void testGermanForAllCodes ()
  {
    final IUnitCode aKilogram = UnitCodes.getFromIDOrNull ("KGM");
    assertNotNull (aKilogram);
    assertEquals ("kilogram", aKilogram.getDisplayText (Locale.UK));
    assertEquals ("kilogram", aKilogram.getDisplayText (Locale.ENGLISH));
    assertEquals ("Kilogramm", aKilogram.getDisplayText (Locale.GERMAN));
    assertEquals ("Kilogramm", aKilogram.getDisplayText (Locale.GERMANY));

    for (final IUnitCode aUnit : UnitCodes.getAll ())
    {
      final String sText = aUnit.getDisplayText (Locale.GERMAN);
      assertNotNull (aUnit.getID (), sText);
      assertFalse (aUnit.getID (), sText.isBlank ());
    }
  }
}
