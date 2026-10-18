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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;
import com.helger.base.name.IHasDisplayName;

/**
 * The UN/ECE recommendation a unit code was defined in.
 *
 * @author Philip Helger
 */
public enum EUnitCodeSource implements IHasID <String>, IHasDisplayName
{
  /** UN/ECE Recommendation N&deg;20 - Codes for Units of Measure Used in International Trade */
  REC20 ("rec20", "UN/ECE Recommendation N\u00b020"),
  /** UN/ECE Recommendation N&deg;21 - Codes for Passengers, Types of Cargo, Packages and Packaging Materials */
  REC21 ("rec21", "UN/ECE Recommendation N\u00b021");

  private final String m_sID;
  private final String m_sDisplayName;

  EUnitCodeSource (@NonNull @Nonempty final String sID, @NonNull @Nonempty final String sDisplayName)
  {
    m_sID = sID;
    m_sDisplayName = sDisplayName;
  }

  /**
   * @return The identifier as used in the data file. Neither <code>null</code> nor empty.
   */
  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  /**
   * @return The English name of the recommendation. Neither <code>null</code> nor empty.
   */
  @NonNull
  @Nonempty
  public String getDisplayName ()
  {
    return m_sDisplayName;
  }

  /**
   * Find the source with the provided identifier.
   *
   * @param sID
   *        The identifier to search. May be <code>null</code>.
   * @return <code>null</code> if no such source exists.
   */
  @Nullable
  public static EUnitCodeSource getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EUnitCodeSource.class, sID);
  }
}
