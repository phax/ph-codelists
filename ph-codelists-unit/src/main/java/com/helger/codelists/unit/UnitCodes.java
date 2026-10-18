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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonnegative;
import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.io.nonblocking.NonBlockingBufferedReader;
import com.helger.base.string.StringHelper;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.CommonsLinkedHashMap;
import com.helger.collection.commons.ICommonsList;
import com.helger.collection.commons.ICommonsOrderedMap;

/**
 * The unit codes of UN/ECE Recommendation N&deg;20 (units of measure) and N&deg;21 (packaging),
 * 2162 codes together.
 * <p>
 * The data are the sheet <code>Unit</code> of the EN 16931 code list workbook, which is the list
 * that BT-130 and BT-150 of EN 16931 refer to. They do not fit into an enum - the static
 * initialiser of an enum of that size exceeds the limit of the JVM - and are therefore read lazily
 * from the resource <code>codelists/unece-units.tsv</code>.
 *
 * @author Philip Helger
 */
@Immutable
public final class UnitCodes
{
  /**
   * The base name of the resource bundles with translated names. The bundle of a locale is
   * <code>ph-codelists-unit_&lt;locale&gt;.properties</code>.
   */
  public static final String BUNDLE_BASE_NAME = "ph-codelists-unit";

  /** The classpath location of the code list data. */
  public static final String DATA_RESOURCE = "codelists/unece-units.tsv";

  private static final class SingletonHolder
  {
    static final ICommonsOrderedMap <String, UnitCode> MAP = _readAll ();

    private static ICommonsOrderedMap <String, UnitCode> _readAll ()
    {
      final ICommonsOrderedMap <String, UnitCode> ret = new CommonsLinkedHashMap <> ();
      try (final InputStream aIS = UnitCodes.class.getClassLoader ().getResourceAsStream (DATA_RESOURCE))
      {
        if (aIS == null)
          throw new IllegalStateException ("Failed to find the classpath resource '" + DATA_RESOURCE + "'");

        try (final NonBlockingBufferedReader aReader = new NonBlockingBufferedReader (new InputStreamReader (aIS,
                                                                                                             StandardCharsets.UTF_8)))
        {
          // The first line is the header
          String sLine = aReader.readLine ();
          while ((sLine = aReader.readLine ()) != null)
          {
            if (sLine.isEmpty ())
              continue;

            final List <String> aParts = StringHelper.getExploded ('\t', sLine);
            if (aParts.size () != 3)
              throw new IllegalStateException ("Invalid line in '" + DATA_RESOURCE + "': " + sLine);

            final EUnitCodeSource eSource = EUnitCodeSource.getFromIDOrNull (aParts.get (0));
            if (eSource == null)
              throw new IllegalStateException ("Invalid source in '" + DATA_RESOURCE + "': " + sLine);

            final UnitCode aCode = new UnitCode (aParts.get (1), eSource, aParts.get (2));
            if (ret.put (aCode.getID (), aCode) != null)
              throw new IllegalStateException ("Duplicate code in '" + DATA_RESOURCE + "': " + sLine);
          }
        }
      }
      catch (final IOException ex)
      {
        throw new UncheckedIOException (ex);
      }
      return ret;
    }
  }

  private UnitCodes ()
  {}

  /**
   * @return The number of codes in the list.
   */
  @Nonnegative
  public static int getCount ()
  {
    return SingletonHolder.MAP.size ();
  }

  /**
   * @return All codes in the order of the data file. Never <code>null</code>.
   */
  @NonNull
  @ReturnsMutableCopy
  public static ICommonsList <IUnitCode> getAll ()
  {
    return new CommonsArrayList <> (SingletonHolder.MAP.values ());
  }

  /**
   * Find the unit code with the provided code. The comparison is case sensitive.
   *
   * @param sID
   *        The code to search. May be <code>null</code>.
   * @return <code>null</code> if the code list contains no such code.
   */
  @Nullable
  public static IUnitCode getFromIDOrNull (@Nullable final String sID)
  {
    return SingletonHolder.MAP.get (sID);
  }

  /**
   * Check if the provided code is part of the list.
   *
   * @param sID
   *        The code to check. May be <code>null</code>.
   * @return <code>true</code> if the list contains the code.
   */
  public static boolean containsID (@Nullable final String sID)
  {
    return SingletonHolder.MAP.containsKey (sID);
  }
}
